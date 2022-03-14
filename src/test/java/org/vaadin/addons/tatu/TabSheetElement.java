package org.vaadin.addons.tatu;

import java.util.List;

import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.tabs.testbench.TabsElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

@Element("tab-sheet")
public class TabSheetElement extends TestBenchElement {

    public void setSelectedTabIndex(int i) {
        TabsElement tabsElement = this.$(TabsElement.class).attribute("part", "tabs").first();
        tabsElement.setSelectedTabIndex(i);
    }

    public boolean isSheetDisplayed(int i) {
        List<DivElement> divs = this.$(DivElement.class).attribute("part","sheet").all();
        return divs.get(i).isDisplayed();
    }
}
