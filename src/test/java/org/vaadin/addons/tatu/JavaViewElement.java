package org.vaadin.addons.tatu;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;

@Attribute(name = "id", value = "java-view")
public class JavaViewElement extends TestBenchElement {

    public TabSheetElement getTabSheet() {
        return this.$(TabSheetElement.class).first();        
    }    
}
