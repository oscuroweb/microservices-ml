package oscuroweb.ia.screen;

import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.rest.RestClient;

@Slf4j
public abstract class AbstractIncomeView extends ResponsiveLayout implements View {

	private static final long serialVersionUID = 1061490086976065114L;
	
	@Autowired
	protected RestClient restclient;
	
	@Override
	public void enter(ViewChangeEvent request) {
		log.info("Start {}", this.getClass().getSimpleName());
		this.removeAllComponents();
		this.setSizeFull();
		this.setContainerType(ContainerType.FLUID);
		this.setSpacing();
		this.withDefaultRules(12, 12, 6, 4);
		this.withFlexible();
		this.setId("content-layout");

		this.initView();
		
		this.initializeComponentsIds();
		
	}

	protected abstract void initView();
	


	private void initializeComponentsIds() {
		
		Arrays.asList(this.getClass().getDeclaredFields())
				.stream()
				.filter(field -> Component.class.isInstance(field))
				.map(field -> {
					try {
						return field.get(this);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return field;
				})
				.forEach(component -> ((Component) component).setId("id".concat(String.valueOf(new Random().nextInt()))));
		
		
		
	}
	
	
	protected ResponsiveRow createRow() {
		return this.addRow()
				.withGrow(true)
				.withSpacing(true)
				.withMargin(true);
	}
	
	protected ResponsiveRow createRow(int xs, int sm, int md, int lg) {
		return this.addRow()
				.withGrow(true)
				.withSpacing(true)
				.withDefaultRules(xs, sm, md, lg)
				.withMargin(true);
	}

	protected void addColumn(ResponsiveRow row, Component component) {
		component.setWidth(100, Unit.PERCENTAGE);
		row.addColumn().withComponent(component);
	}
	
	protected void addColumns(ResponsiveRow row, Component ... components) {
		Arrays.asList(components).stream().forEach(component -> addColumn(row, component));
	}
	
	

}
