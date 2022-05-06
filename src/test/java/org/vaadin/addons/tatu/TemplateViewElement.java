package org.vaadin.addons.tatu;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

@Element("tab-sheet-view")
public class TemplateViewElement extends TestBenchElement {

    public TabSheetElement getTabSheet() {
        return this.$(TabSheetElement.class).first();
    }
}
