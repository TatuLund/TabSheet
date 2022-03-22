package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.testbench.TestBenchElement;

public class TabSheetComponentIT extends AbstractViewTest {

    @Test
    public void componentWorksJava() throws IOException {

        JavaViewElement javaView = $(MainLayoutElement.class).first()
                .navigateJava();
        TabSheetElement tabSheet = javaView.getTabSheet();
        tabSheet.setSelectedTabIndex(2);
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Index: '2'"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(2));
    }
        
    @Test
    public void componentWorksTemplate() throws IOException {

        TemplateViewElement template = $(MainLayoutElement.class).first()
                .navigateTemplate();
        TabSheetElement tabSheet = template.getTabSheet();
        tabSheet.setSelectedTabIndex(1); 
        NotificationElement notification = $(NotificationElement.class).last();
        Assert.assertTrue(notification.getText().startsWith("Tab 1"));
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        Assert.assertTrue(tabSheet.isSheetDisplayed(1));
    }
}
