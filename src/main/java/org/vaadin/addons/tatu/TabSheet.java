package org.vaadin.addons.tatu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.shared.Registration;

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
public class TabSheet extends Component implements HasSize {

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
        String tab = "tab" + (captions.size());
        addTab(caption, tab, content);
        return tab;
    }

    /**
     * Get Component sheet of the tab.
     * 
     * @param index The index of the tab, base 0
     * @return Component used as sheet for tab in index
     */
    public Component getComponent(int index) {
        checkBounds(index);
        return getComponent(tabs.get(index)).get();
    }

    private void checkBounds(int index) {
        if (index < 0 || index > this.getElement().getChildCount() - 1) {
            throw new IllegalArgumentException(
                    "Tab index needs to be between 0 and " + (tabs.size() - 1));
        }
    }

    /**
     * Get Component sheet of the tab.
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
     * Remove tab and sheet based on index.
     * 
     * @param index Index of the tab and sheet to be removed
     */
    public void removeTab(int index) {
        checkBounds(index);
        removeTab(tabs.get(index));
    }

    public void removeTab(String tab) {
        Objects.requireNonNull(tab, "tab cant be null");
        int index = tabs.indexOf(tab);
        if (index >= 0) {
            removeTabEntry(index);
            getElement().removeChild(getComponent(tab).get().getElement());
        }
    }

    private void removeTabEntry(int index) {
        tabs.remove(index);
        captions.remove(index);
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
    public String getTab(int index) {
        checkBounds(index);
        return tabs.get(index);
    }

    /**
     * Get the caption String of the tab in index.
     * 
     * @param index Index, base 0
     * @return The caption
     */
    public String getCaption(int index) {
        checkBounds(index);
        return captions.get(index);
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
            return Optional.ofNullable(null);
        } else {
            return Optional.of(captions.get(index));
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
        int index = tabs.indexOf(tab);
        if (index == -1) {
            throw new IllegalArgumentException("tab " + tab + " not defined");
        } else {
            setSelected(index);
        }
    }

    /**
     * Set selected tab using index. This will fire TabChangeEvent.
     * Sheet attached to the tab will be shown.
     * 
     * @param index Index of the tab, base 0.
     */
    public void setSelected(int index) {
        checkBounds(index);
        getElement().setProperty("selected", index);
    }

    /**
     * Change the caption string used by tab.
     * 
     * @param tab Unique tab identifier
     * @param caption The new caption string
     */
    public void setCaption(String tab, String caption) {
        Objects.requireNonNull(caption, "caption cant be null");
        Objects.requireNonNull(tab, "tab cant be null");
        int index = tabs.indexOf(tab);
        if (index >= 0) {
                setCaption(index, caption);
        }        
    }

    /**
     * Change the caption string used by tab.
     * 
     * @param index The index, base 0
     * @param caption The new caption string
     */
    public void setCaption(int index, String caption) {
        checkBounds(index);
        captions.set(index, caption);
        getElement().getChild(index).setAttribute("tabcaption", caption);
    }

    /**
     * TabChangeEvent is fired when user changes the  
     *
     * @param <R> Parameter is here, so that TabSheet can be extended.
     */
    @DomEvent("tab-changed")
    public static class TabChangedEvent<R extends TabSheet>
            extends ComponentEvent<TabSheet> {
        private int page;
        private TabSheet source;

        public TabChangedEvent(TabSheet source, boolean fromClient,
                @EventData("event.detail") int page) {
            super(source, fromClient);
            this.page = page;
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
            return source.getTab(page);
        }

        /**
         * Get the caption of the selected tab.
         * 
         * @return The caption
         */
        public String getCaption() {
            return source.getCaption(page);
        }

        /**
         * Get Component used as sheet for the selected tab.
         * 
         * @return Component used as sheet for the selected tab
         */
        public Component getComponent() {
        	return source.getComponent(page);
        }
 
        /**
         * Get the index of the selected tab.
         * 
         * @return The index of the selected tab, base 0
         */
        public int getIndex() {
            return page;
        }
    }

}
