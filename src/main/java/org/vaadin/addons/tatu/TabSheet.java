package org.vaadin.addons.tatu;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonObject;

/**
 * A basic TabSheet composed using vaadin-tabs and vaadin-tab. The client side
 * of the component is implemented as web component using LitElement, written in
 * TypeScript.
 * 
 * @author Tatu Lund
 *
 */
@JsModule("./tab-sheet.ts")
@Tag("tab-sheet")
@Uses(Icon.class)
@Uses(Tabs.class)
@Uses(Tab.class)
public class TabSheet extends Component implements HasSize, HasTheme {

    public enum TabSheetVariant {
        LUMO_ICON_ON_TOP("icon-on-top"), LUMO_CENTERED("centered"), LUMO_SMALL(
                "small"), LUMO_MINIMAL("minimal"), LUMO_HIDE_SCROLL_BUTTONS(
                        "hide-scroll-buttons"), LUMO_EQUAL_WIDTH_TABS(
                                "equal-width-tabs"), BORDERED(
                                        "bordered"), MATERIAL_FIXED("fixed");

        private final String variant;

        TabSheetVariant(String variant) {
            this.variant = variant;
        }

        /**
         * Gets the variant name.
         * 
         * @return variant name
         */
        public String getVariantName() {
            return variant;
        }
    }

    /***
     * The default constructor.
     */
    public TabSheet() {
    }

    /**
     * /** Add a new component to the TabSheet as a new sheet.
     * 
     * @param caption
     *            Caption string used in corresponding Tab
     * @param content
     *            The content Component
     * @param icon
     *            Icon to be used on tab, can be null
     */
    public void addTab(String caption, Component content, VaadinIcon icon) {
        Objects.requireNonNull(caption, "caption must be defined");
        Objects.requireNonNull(content, "content must be defined");
        content.getElement().setAttribute("tabcaption", caption);
        if (icon != null) {
            content.getElement().setAttribute("tabicon", getIcon(icon));
        }
        getElement().appendChild(content.getElement());
    }

    private String getIcon(VaadinIcon vaadinIcon) {
        if (vaadinIcon == null) {
            return null;
        } else {
            return "vaadin:" + fixIconName(String.valueOf(vaadinIcon));
        }
    }

    private String fixIconName(String name) {
        String trimmed;
        trimmed = name.toLowerCase();
        trimmed = trimmed.replace("_", "-");
        return trimmed;
    }

    /**
     * Add a new component to the TabSheet as a new sheet.
     * 
     * @param caption
     *            Caption string used in corresponding Tab
     * @param content
     *            The content Component
     */
    public void addTab(String caption, Component content) {
        addTab(caption, content, null);
    }

    /**
     * Get optional Component sheet of the tab.
     * <p>
     * Note: If tab-sheet and component is defined in template, the components
     * are not available.
     * 
     * @param tab
     *            The tab identifier of the tab
     * @return Optional Component used as sheet for tab in index
     */
    public Optional<Component> getComponent(String tab) {
        Objects.requireNonNull(tab, "tab cant be null");
        return getChildren().filter(
                comp -> comp.getElement().getAttribute("slot").equals(tab))
                .findFirst();
    }

    /**
     * Remove tab and sheet based on tab.
     * 
     * @param tab
     *            Tab identifier of the tab to be removed
     */
    public void removeTab(String tab) {
        getElement().executeJs("this.removeTab($0)", tab);
    }

    /**
     * Add listener for Tab change events.
     * 
     * @param listener
     *            Functional interface, lambda expression of the listener
     *            callback function.
     * @return Listener registration. Use {@link Registration#remove()} to
     *         remove the listener.
     */
    public Registration addTabChangedListener(
            ComponentEventListener<TabChangedEvent<TabSheet>> listener) {
        return addListener(TabChangedEvent.class,
                (ComponentEventListener) listener);
    }

    /**
     * Get the tab identified based on index.
     * 
     * @param index
     *            Index of the tab, base 0
     * @return Unique tab identifier
     */
    public String getTab(int index) {
        return "tab" + index;
    }

    /**
     * Set selected tab using identifier. This will fire TabChangeEvent. Sheet
     * attached to the tab will be shown.
     * 
     * @param tab
     *            Unique tab identifier.
     */
    public void setSelected(String tab) {
        Objects.requireNonNull(tab, "tab cant be null");
        getElement().executeJs("this.setSelectedTab($0)", tab);
    }

    /**
     * Set selected tab using index. This will fire TabChangeEvent. Sheet
     * attached to the tab will be shown.
     * 
     * @param index
     *            Index of the tab, base 0.
     */
    public void setSelected(int index) {
        getElement().setProperty("selected", index);
    }

    /**
     * Change the caption string used by tab.
     * 
     * @param tab
     *            Unique tab identifier
     * @param caption
     *            The new caption string
     */
    public void setCaption(String tab, String caption) {
        getElement().executeJs("this.setCaption($0,$1,$2)", -1, caption, tab);
    }

    /**
     * Adds theme variants to the component.
     *
     * @param variants
     *            theme variants to add
     */
    public void addThemeVariants(TabSheetVariant... variants) {
        getThemeNames()
                .addAll(Stream.of(variants).map(TabSheetVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * Removes theme variants from the component.
     *
     * @param variants
     *            theme variants to remove
     */
    public void removeThemeVariants(TabSheetVariant... variants) {
        getThemeNames().removeAll(
                Stream.of(variants).map(TabSheetVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * Sets the orientation of this tab sheet.
     *
     * @param orientation
     *            the orientation
     */
    public void setOrientation(Orientation orientation) {
        getElement().setAttribute("orientation",
                orientation.name().toLowerCase());
    }

    /**
     * TabChangeEvent is fired when user changes the
     *
     * @param <R>
     *            Parameter is here, so that TabSheet can be extended.
     */
    @DomEvent("tab-changed")
    public static class TabChangedEvent<R extends TabSheet>
            extends ComponentEvent<TabSheet> {
        private int index;
        private TabSheet source;
        private String caption;
        private String tab;

        public TabChangedEvent(TabSheet source, boolean fromClient,
                @EventData("event.detail") JsonObject details) {
            super(source, fromClient);
            this.index = (int) details.getNumber("index");
            this.tab = details.getString("tab");
            this.caption = details.getString("caption");
            this.source = source;
        }

        /**
         * Get the TabSheet which fired the event.
         * 
         * return The source TabSheet
         */
        public TabSheet getSource() {
            return source;
        }

        /**
         * Get the tab identifier. Of the selected tab.
         * 
         * @return The unique Tab identifier
         */
        public String getTab() {
            return tab;
        }

        /**
         * Get the caption of the selected tab.
         * 
         * @return The caption
         */
        public String getCaption() {
            return caption;
        }

        /**
         * Get Component used as sheet for the selected tab.
         * 
         * @return Component used as sheet for the selected tab
         */
        public Optional<Component> getComponent() {
            return source.getComponent(tab);
        }

        /**
         * Get the index of the selected tab.
         * 
         * @return The index of the selected tab, base 0
         */
        public int getIndex() {
            return index;
        }
    }

}