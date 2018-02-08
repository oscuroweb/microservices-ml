package oscuroweb.ia.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

	private static final long serialVersionUID = -5510796932223511405L;

	Navigator navigator;

	private Panel springViewDisplay;

	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle("Income Predictor");

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);

		VerticalLayout banner = new VerticalLayout();
		banner.addStyleNames("banner");
		banner.setId("banner");
		banner.setWidth(100, Unit.PERCENTAGE);
		banner.setSpacing(false);

		root.addComponent(banner);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);

	}

}
