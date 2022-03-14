package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.testbench.TestBenchElement;

public class ViewIT extends AbstractViewTest {

    @Test
    public void componentWorksJava() throws IOException {
        
        findElement(By.id("java-link")).click();
        final TabSheetElement tabSheet = $(TabSheetElement.class).first();
        tabSheet.setSelectedTabIndex(2);
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Index: '2'"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(2));
      }

//    @Test
//    public void componenNottWorksTemplate() throws IOException {
//        
//        findElement(By.id("template-link")).click();
//        final TestBenchElement tabSheet = $("tab-sheet").first();
//        Assert.assertTrue(tabSheet.$(TestBenchElement.class).all().size() > 0);
//    }
    
    @Test
    public void componentWorksTemplate() throws IOException {
        
        findElement(By.id("template-link")).click();
        TestBenchElement template = $("tab-sheet-view").id("template");
        final TabSheetElement tabSheet = template.$(TabSheetElement.class).first();
        tabSheet.setSelectedTabIndex(1);
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Tab 1"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(1));
    }
}
