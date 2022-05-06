package org.vaadin.addons.tatu;

import java.util.Arrays;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

//@Push(transport = Transport.LONG_POLLING)
@Theme(value = "mytheme")
public class MainLayout extends AppLayout implements AppShellConfigurator {

    public MainLayout() {

        VerticalLayout menu = new VerticalLayout();
        this.addToDrawer(menu);
        RouterLink javaLink = new RouterLink("Java view", JavaView.class);
        RouterLink templateLink = new RouterLink("Template view",
                TemplateView.class);
        javaLink.setId("java-link");
        templateLink.setId("template-link");
        menu.add(javaLink, templateLink);
        Checkbox variant = new Checkbox("Dark");
        variant.addValueChangeListener(event -> {
            if (event.getValue()) {
                setVariant(Lumo.DARK);
            } else {
                setVariant(Lumo.LIGHT);
            }
        });
        addToDrawer(variant);
    }

    public void setVariant(String variant) {
        assert Arrays.asList(Lumo.DARK, Lumo.LIGHT).contains(variant);
        getElement().getThemeList()
                .removeAll(Arrays.asList(Lumo.DARK, Lumo.LIGHT));
        getElement().getThemeList().add(variant);
    }

}
