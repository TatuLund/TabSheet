package org.vaadin.addons.tatu;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class TabSheetInPolymerTemplateIT extends AbstractViewTest {

    public TabSheetInPolymerTemplateIT() {
        super("tab-sheet-polymer");
    }

    @Test
    public void tabSheetWorksOnPolymerTemplate() {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();        
        Assert.assertTrue(tabSheet.isSheetDisplayed(0));
        tabSheet.setSelectedTabIndex(1); 
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(1));    }
}
