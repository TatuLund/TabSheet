package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class TabSheetInLitTemplateIT extends AbstractViewTest {

    public TabSheetInLitTemplateIT() {
        super("tab-sheet-lit");
    }
    
    @Test
    public void componentWorksLit() throws IOException {
        TemplateViewElement template = $(TemplateViewElement.class).first();        
        TabSheetElement tabSheet = template.getTabSheet();        
        Assert.assertTrue(tabSheet.isSheetDisplayed(0));
        tabSheet.setSelectedTabIndex(1);
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Tab 1"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(1));
    }
}
