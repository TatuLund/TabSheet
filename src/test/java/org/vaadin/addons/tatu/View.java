package org.vaadin.addons.tatu;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class View extends Div {

    public View() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.getElement().getThemeList().add(TabsVariant.LUMO_CENTERED.getVariantName());
        tabSheet.addTab("First tab", createTabContent("400px"), VaadinIcon.TAB);
        tabSheet.addTab("Second tab", createTabContent("500px"));
        tabSheet.addTab("Third tab", createTabContent("600px"));
        tabSheet.addTab("Fourth tab", createTabContent("700px"));
        tabSheet.addTabChangedListener(event -> {
            Notification.show("Index: '"+event.getIndex()+"' Caption: '"+event.getCaption()+"' Tab: '"+event.getTab()+"'");
        });
        tabSheet.setWidth("50%");
        tabSheet.setHeight("250px");
        tabSheet.setSelected(2);
        tabSheet.setCaption(tabSheet.getTab(0), "First area");
        
        add(tabSheet);
    }

    public Div createTabContent(String width) {
    	Div div = new Div();
    	div.getStyle().set("background", "#f0f0f0");
    	TextArea area = new TextArea();
        area.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum cursus velit sed libero efficitur, ut tristique lorem dignissim. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed lacinia augue mauris, eget mollis orci vestibulum nec. Pellentesque ultrices est tempor venenatis varius. Maecenas justo neque, dictum vitae nisi ac, venenatis lacinia nisl. Duis tristique elit vel risus molestie ultricies. Nulla non nunc mi. Nullam et dolor quis elit viverra dictum a in neque. Nullam pulvinar dolor sed purus pharetra, sed pellentesque mi eleifend. Vestibulum tempus ligula a augue pulvinar tincidunt. Donec sit amet purus consectetur, vestibulum arcu sed, venenatis ex. Morbi a sem sollicitudin, lacinia libero rutrum, tempus diam.");
        area.setWidth(width);
        div.add(area);
    	return div;
    }
}
