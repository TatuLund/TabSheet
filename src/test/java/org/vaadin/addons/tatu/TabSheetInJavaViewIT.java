package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class TabSheetInJavaViewIT extends AbstractViewTest {

    public TabSheetInJavaViewIT() {
        super("tab-sheet-java");
    }
    
    @Test
    public void componentWorksJava() throws IOException {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();        
        // By default tab 0 is shown
        Assert.assertTrue(tabSheet.isSheetDisplayed(0));
        tabSheet.setSelectedTabIndex(2);
        NotificationElement notification = $(NotificationElement.class).last();
        // Assert that event shows correct tab info
        Assert.assertTrue(notification.getText().startsWith("Index: '2'"));
        // Assert that tab 0 is not shown anymer
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        // Assert that tab 2 is now shown instead
        Assert.assertTrue(tabSheet.isSheetDisplayed(2));
    }
}
