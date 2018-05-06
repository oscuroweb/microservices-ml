package oscuroweb.ia.component;

import com.jarektoro.responsivelayout.ResponsiveColumn.ColumnComponentAlignment;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class Header extends ResponsiveLayout {

	private static final long serialVersionUID = -7281983107427138417L;

    private Image imageLogo;

	public Header() {
        this.setWidth(100, Unit.PERCENTAGE);
        this.setHeight(20, Unit.PERCENTAGE);    

        build();
    }

    public void build() {
        imageLogo = new Image();

        imageLogo.setSource(new ThemeResource("images/logo_osc.png"));
        imageLogo.setHeight(60, Unit.PIXELS);
        
        Label title = new Label("Microservices & Machine Learning");
        title.addStyleNames(ValoTheme.LABEL_HUGE, ValoTheme.LABEL_BOLD, "title-color");
        
        ResponsiveRow headerRow = this.addRow()
				.withGrow(true)
				.withSpacing(true)
				.withMargin(true);
        
        headerRow.addColumn()
        		.withComponent(imageLogo, ColumnComponentAlignment.LEFT);
        headerRow.addColumn()
        		.withVisibilityRules(false, false, true, true)
        		.withComponent(title, ColumnComponentAlignment.RIGHT);
        	
        

    }
	
}
