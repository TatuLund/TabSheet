package org.vaadin.addons.tatu;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.RouterLink;

// For future, should work in Vaadin 23, see https://github.com/vaadin/web-components/issues/401
@CssImport(value = "./tab-styles.css", themeFor="tab-sheet")
public class MainLayout extends AppLayout implements AppShellConfigurator {

	public MainLayout() {
		VerticalLayout menu = new VerticalLayout();
		this.addToDrawer(menu);
		menu.add(
				new RouterLink("Java view",View.class),
				new RouterLink("Template view",TemplateView.class));
	}
}
