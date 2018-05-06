package oscuroweb.ia.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Footer extends VerticalLayout {

	private static final long serialVersionUID = 9155040833896797714L;

	public Footer() {

		this.setWidth(100, Unit.PERCENTAGE);
        this.setSpacing(false);
        this.setMargin(true);
        
        // Imagen Logo
        Image masterLogo = new Image();
        masterLogo.setSource(new ThemeResource("images/logoA2BD.png"));
        masterLogo.setHeight(90, Unit.PIXELS);
        
        VerticalLayout labelLayout = new VerticalLayout();
        HorizontalLayout titleLayout = new HorizontalLayout();
        Label titleLabel = new Label("Advanced Analytics on Big Data");
        Label masterLabel = new Label("Master Thesis");
        titleLayout.addComponents(titleLabel, masterLabel);
        titleLayout.setComponentAlignment(titleLabel, Alignment.MIDDLE_RIGHT);
        titleLayout.setComponentAlignment(masterLabel, Alignment.MIDDLE_LEFT);
        
        Label authorLabel = new Label("Developed by Rafael Hidalgo Calero");
        authorLabel.setSizeFull();
        labelLayout.addComponents(titleLayout, authorLabel);
        labelLayout.setComponentAlignment(authorLabel, Alignment.MIDDLE_CENTER);
        labelLayout.setHeight(30, Unit.PIXELS);
        titleLabel.addStyleNames("banner-label", "banner-label-title");
        masterLabel.addStyleNames("banner-label", "banner-label-master");
        authorLabel.addStyleNames("banner-label", "banner-label-author");
        
        titleLayout.setMargin(false);
        titleLayout.setSpacing(false);
        labelLayout.setMargin(false);
        labelLayout.setSpacing(false);

        // Imagen Logo
        Image umaLogo = new Image();
        umaLogo.setSource(new ThemeResource("images/UMA_logo.png"));
        umaLogo.setHeight(50, Unit.PIXELS);
        
        
        
        HorizontalLayout bannerLayout = new HorizontalLayout(masterLogo, labelLayout, umaLogo);
        bannerLayout.setComponentAlignment(masterLogo, Alignment.MIDDLE_LEFT);
        bannerLayout.setComponentAlignment(labelLayout, Alignment.MIDDLE_CENTER);
        bannerLayout.setComponentAlignment(umaLogo, Alignment.MIDDLE_RIGHT);
        bannerLayout.setWidth(100, Unit.PERCENTAGE);
        bannerLayout.setHeight(100, Unit.PIXELS);

        this.addComponent(bannerLayout);
        this.setSizeFull();
        this.addStyleName("footer-color");
        this.setComponentAlignment(bannerLayout, Alignment.BOTTOM_CENTER);

	}
	
}
