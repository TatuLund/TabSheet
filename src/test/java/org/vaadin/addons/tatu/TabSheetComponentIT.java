package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class TabSheetComponentIT extends AbstractViewTest {

    @Test
    public void componentWorksJava() throws IOException {

        JavaViewElement javaView = $(MainLayoutElement.class).first()
                .navigateJava();
        TabSheetElement tabSheet = javaView.getTabSheet();
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
        
    @Test
    public void componentWorksTemplate() throws IOException {

        TemplateViewElement template = $(MainLayoutElement.class).first()
                .navigateTemplate();
        TabSheetElement tabSheet = template.getTabSheet();
        Assert.assertTrue(tabSheet.isSheetDisplayed(0));
        tabSheet.setSelectedTabIndex(1); 
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Tab 1"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(1));
    }
}
