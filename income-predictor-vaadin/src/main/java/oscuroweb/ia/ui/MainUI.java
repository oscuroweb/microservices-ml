package oscuroweb.ia.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.component.Footer;
import oscuroweb.ia.component.Header;

@Slf4j
@Theme("income")
@SpringUI
@SpringViewDisplay
@Viewport("width=device-width, initial-scale=1")
public class MainUI extends UI {

	private static final long serialVersionUID = -5510796932223511405L;

	Navigator navigator;

	@Autowired
	private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle("Income Predictor");

		this.setSizeFull();

		BorderLayout mainLayout = new BorderLayout();
		//mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false);

		Header header = new Header();
		Footer footer = new Footer();
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();

		mainLayout.addComponent(header, BorderLayout.Constraint.PAGE_START);
		//mainLayout.addComponent(footer, BorderLayout.Constraint.PAGE_END);
		mainLayout.addComponent(content, BorderLayout.Constraint.CENTER);
		
		setContent(mainLayout);

		navigator = new Navigator(this, content);
		navigator.addProvider(viewProvider);

		this.setErrorHandler(event -> {
			Throwable ex = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			Notification.show("ErrorHandler", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			log.error(ex.getMessage(), ex);
		});
	}

}
