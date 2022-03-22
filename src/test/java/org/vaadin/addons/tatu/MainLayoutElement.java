package org.vaadin.addons.tatu;

import org.openqa.selenium.By;

import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-app-layout")
public class MainLayoutElement extends AppLayoutElement {

    public JavaViewElement navigateJava() {
        findElement(By.id("java-link")).click();
        return $(JavaViewElement.class).first();        
    }

    public TemplateViewElement navigateTemplate() {
        findElement(By.id("template-link")).click();
        return $(TemplateViewElement.class).first();   
    }
}
