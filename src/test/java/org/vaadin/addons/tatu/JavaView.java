package org.vaadin.addons.tatu;

import org.vaadin.addons.tatu.TabSheet.TabSheetVariant;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;

@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "tab-sheet-java")
public class JavaView extends Div {

    public JavaView() {
        setId("java-view");
        getStyle().set("margin", "10px");
        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_CENTERED);
        tabSheet.addTab("First tab", createTabContent("400px"),
                VaadinIcon.TEXT_INPUT);
        tabSheet.addTab("Second tab", createTabContent("500px"),
                VaadinIcon.TEXT_INPUT);
        tabSheet.addTab("Third tab", createTabContent("600px"),
                VaadinIcon.TEXT_INPUT);
        tabSheet.addTab("Fourth tab", createTabContent("700px"),
                VaadinIcon.TEXT_INPUT);
        tabSheet.addTab(" ", new Div(), VaadinIcon.PLUS);

        tabSheet.addTabChangedListener(event -> {
            Notification.show("Index: '" + event.getIndex() + "' Caption: '"
                    + event.getCaption() + "' Tab: '" + event.getTab() + "'");
            if (event.getCaption() != null && event.getCaption().equals(" ")) {
                tabSheet.removeTab(event.getTab());
                tabSheet.addTab("New", new Span("New tab"));
                tabSheet.addTab(" ", new Div(), VaadinIcon.PLUS);
            }
        });
        // tabSheet.getElement().getStyle().set("--lumo-border-radius-m",
        // "10px");

        tabSheet.setWidth("50%");
        tabSheet.setHeight("300px");
        // tabSheet.setSelected(2);
        tabSheet.setCaption(tabSheet.getTab(0), "First area");

        Checkbox blue = new Checkbox("BLUE");
        blue.addValueChangeListener(event -> {
            if (event.getValue()) {
                tabSheet.addThemeName("blue");
            } else {
                tabSheet.removeThemeName("blue");
            }
        });

        Checkbox orientation = new Checkbox("VERTICAL");
        orientation.addValueChangeListener(event -> {
            if (event.getValue()) {
                tabSheet.setOrientation(Orientation.VERTICAL);
            } else {
                tabSheet.setOrientation(Orientation.HORIZONTAL);
            }
        });

        CheckboxGroup<TabSheetVariant> themes = new CheckboxGroup<>();
        themes.setItems(TabSheetVariant.values());
        themes.addValueChangeListener(event -> {
            tabSheet.getElement().getThemeList().clear();
            tabSheet.addThemeVariants(
                    event.getValue().toArray(new TabSheetVariant[0]));
        });
        themes.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        IntegerField intField = new IntegerField();
//        intField.setHasControls(true);
        intField.setMax(10);
        intField.setMin(0);
        intField.addValueChangeListener(event -> {
            Notification.show("Invalid: "+intField.isInvalid()+" Value: "+event.getValue());
        });

        add(tabSheet, orientation, themes, blue, intField);
    }

    public Div createTabContent(String width) {
        Div div = new Div();
        TextArea area = new TextArea("Text");
        area.setValue(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum cursus velit sed libero efficitur, ut tristique lorem dignissim. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed lacinia augue mauris, eget mollis orci vestibulum nec. Pellentesque ultrices est tempor venenatis varius. Maecenas justo neque, dictum vitae nisi ac, venenatis lacinia nisl. Duis tristique elit vel risus molestie ultricies. Nulla non nunc mi. Nullam et dolor quis elit viverra dictum a in neque. Nullam pulvinar dolor sed purus pharetra, sed pellentesque mi eleifend. Vestibulum tempus ligula a augue pulvinar tincidunt. Donec sit amet purus consectetur, vestibulum arcu sed, venenatis ex. Morbi a sem sollicitudin, lacinia libero rutrum, tempus diam.");
        area.setWidth(width);
        div.add(area);
        div.setSizeFull();
        return div;
    }

    public Registration addMyEventListener(
            ComponentEventListener<MyEvent> listener) {
        return addListener(MyEvent.class, listener);
    }
    
    public class MyEvent extends ComponentEvent<JavaView> {
        Object item;
        public MyEvent(JavaView source, Object item,
                boolean fromClient) {
            super(source, fromClient);
            this.item = item;
        }
        Object getItem() {
            return item;
        }
    }
}
