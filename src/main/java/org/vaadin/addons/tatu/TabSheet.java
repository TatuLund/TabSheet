package org.vaadin.addons.tatu;

import java.util.ArrayList;
import java.util.List;
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
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonObject;

/**
 * A basic TabSheet composed using vaadin-tabs and vaadin-tab.
 * The client side of the component is implemented as web component
 * using LitElement, written in TypeScript.
 * 
 * @author Tatu Lund
 *
 */
@JsModule("./tab-sheet.ts")
@Tag("tab-sheet")
public class TabSheet extends Component implements HasSize, HasTheme {

    private List<String> tabs = new ArrayList<>();
    private List<String> captions = new ArrayList<>();

    /***
     * The default constructor.
     */
    public TabSheet() {
    }

    /**
    /**
     * Add a new component to the TabSheet as a new sheet.
     *  
     * @param caption Caption string used in corresponding Tab
     * @param content The content Component
     * @param tab Unique tab indentifier
     */
    public void addTab(String caption, String tab, Component content) {
        if (tabs.indexOf(tab) != -1) {
            throw new IllegalArgumentException(
                    "Tab key " + tab + " already in use");
        }
        captions.add(caption);
        tabs.add(tab);
        content.getElement().setAttribute("slot", tab);
        content.getElement().setAttribute("tabcaption", caption);
        getElement().appendChild(content.getElement());
    }

    /**
     * Add a new component to the TabSheet as a new sheet.
     *  
     * @param caption Caption string used in corresponding Tab
     * @param content The content Component
     * @return Unique tab indentifier
     */
    public String addTab(String caption, Component content) {
        Objects.requireNonNull(caption, "caption cant be null");
        Objects.requireNonNull(content, "content component must be defined");
        String tab = "tab" + (tabs.size());
        addTab(caption, tab, content);
        return tab;
    }


    /**
     * Get optional Component sheet of the tab.
     * <p>
     * Note: If tab-sheet and component is defined in template, the components
     * are not available.
     * 
     * @param tab The tab identifier of the tab
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
     * @param tab Tab identifier of the tab to be removed
     */
    public void removeTab(String tab) {
    	getElement().executeJs("this.removeTab($0)", tab);
    	removeTabEntry(tab);
    }

    private void removeTabEntry(String tab) {
        int index = tabs.indexOf(tab);
        if (index != -1) {
            captions.remove(index);
            tabs.remove(index);
        }
    }

    /**
     * Add listener for Tab change events.
     * 
     * @param listener Functional interface, lambda expression of the listener callback function.
     * @return Listener registration. Use {@link Registration#remove()} to remove the listener.
     */
    public Registration addTabChangedListener(
            ComponentEventListener<TabChangedEvent<TabSheet>> listener) {
        return addListener(TabChangedEvent.class,
                (ComponentEventListener) listener);
    }

    /**
     * Get the tab identified based on index.
     * 
     * @param index Index of the tab, base 0
     * @return Unique tab identifier
     */
    public Optional<String> getTab(int index) {
    	try {
           tabs.get(index);
    	} catch (IndexOutOfBoundsException e) {
    		return Optional.empty();
    	}
        return Optional.of(tabs.get(index));
    }
 
    /**
     * Get the caption String using tab identifier.
     * 
     * @param tab Unique tab identifier
     * @return The caption
     */
    public Optional<String> getCaption(String tab) {
        int index = tabs.indexOf(tab);
        if (index == -1) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(captions.get(index));
        }
    }

    /**
     * Set selected tab using identifier. This will fire TabChangeEvent.
     * Sheet attached to the tab will be shown.
     * 
     * @param tab Unique tab identifier.
     */
    public void setSelected(String tab) {
        Objects.requireNonNull(tab, "tab cant be null");
        getElement().executeJs("this.setSelectedTab($0)",tab);
    }

    /**
     * Set selected tab using index. This will fire TabChangeEvent.
     * Sheet attached to the tab will be shown.
     * 
     * @param index Index of the tab, base 0.
     */
    public void setSelected(int index) {
        getElement().setProperty("selected", index);
    }

    /**
     * Change the caption string used by tab.
     * 
     * @param tab Unique tab identifier
     * @param caption The new caption string
     */
    public void setCaption(String tab, String caption) {
    	int index = tabs.indexOf(tab);
    	if (index != -1) {
    		captions.set(index, caption);
    	}
    	getElement().executeJs("this.setCaption($0,$1,$2)", -1, caption, tab);      
    }

    /**
     * Adds theme variants to the component.
     *
     * @param variants
     *            theme variants to add
     */
    public void addThemeVariants(TabsVariant... variants) {
        getThemeNames()
                .addAll(Stream.of(variants).map(TabsVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * Removes theme variants from the component.
     *
     * @param variants
     *            theme variants to remove
     */
    public void removeThemeVariants(TabsVariant... variants) {
        getThemeNames().removeAll(
                Stream.of(variants).map(TabsVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * TabChangeEvent is fired when user changes the  
     *
     * @param <R> Parameter is here, so that TabSheet can be extended.
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
