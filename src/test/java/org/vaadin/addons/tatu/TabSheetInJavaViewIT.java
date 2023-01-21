package org.vaadin.addons.tatu;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.flow.component.checkbox.testbench.CheckboxElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.tabs.testbench.TabElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.screenshot.ImageFileUtil;

public class TabSheetInJavaViewIT extends AbstractViewTest {

    public TabSheetInJavaViewIT() {
        super("tab-sheet-java");
    }

    @Override
    public void setup() throws Exception {
        super.setup();

        // Hide dev mode gizmo, it would interfere screenshot tests
        $("vaadin-dev-tools").first().setProperty("hidden", true);
    }
 
    @Test
    public void componentWorksJava() {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();        
        // By default tab 0 is shown
        Assert.assertTrue(tabSheet.isSheetDisplayed(0));
        tabSheet.setSelectedTabIndex(2);
        NotificationElement notification = $(NotificationElement.class).last();
        // Assert that event shows correct tab info
        Assert.assertTrue(notification.getText().contains("TO Index: '2'"));
        // Assert that tab 0 is not shown anymer
        Assert.assertFalse(tabSheet.isSheetDisplayed(0));
        // Assert that tab 2 is now shown instead
        Assert.assertTrue(tabSheet.isSheetDisplayed(2));
    }

    @Test
    public void themePropagationWorks() {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();
        CheckboxElement blue = $(CheckboxElement.class).id("blue");
        blue.setChecked(true);
        Assert.assertTrue(tabSheet.getAttribute("theme").contains("blue"));
        TabsElement tabs = tabSheet.getTabs();
        Assert.assertTrue(tabs.getAttribute("theme").contains("blue"));
        tabs.$(TabElement.class).all().forEach(tab -> {
            Assert.assertTrue(tab.getAttribute("theme").contains("blue"));
        });
    }

    @Test
    public void tooltipWorks() {
        Actions action = new Actions(getDriver());
        TabSheetElement tabSheet = $(TabSheetElement.class).first();
        TabElement tab = tabSheet.getTabs().getTabElement("First area");
        action.moveToElement(tab).perform();
        TestBenchElement tooltip = $("vaadin-tooltip-overlay").first();
        Assert.assertEquals("The first tab", tooltip.getText());        
        tab = tabSheet.getTabs().getTabElement("Fourth tab");
        action.moveToElement(tab).perform();
        tooltip = $("vaadin-tooltip-overlay").last();
        Assert.assertEquals("The last tab", tooltip.getText());        
    }

    @Test
    public void themableMixinWorks() throws IOException {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();
        CheckboxElement blue = $(CheckboxElement.class).id("blue");
        blue.setChecked(true);
        Assert.assertTrue(tabSheet.compareScreen(
                ImageFileUtil.getReferenceScreenshotFile("blue-theme.png")));
    }

    @Test
    public void borderedThemeWorks() throws IOException {
        TabSheetElement tabSheet = $(TabSheetElement.class).first();
        TestBenchElement variants = $(TestBenchElement.class).first();
        CheckboxElement bordered = variants.$(CheckboxElement.class).get(7);
        bordered.setChecked(true);
        Assert.assertTrue(tabSheet.compareScreen(
                ImageFileUtil.getReferenceScreenshotFile("bordered-theme.png")));
        CheckboxElement orientation = $(CheckboxElement.class).id("orientation");
        orientation.setChecked(true);
        Assert.assertTrue(tabSheet.compareScreen(
                ImageFileUtil.getReferenceScreenshotFile("bordered-theme-vertical.png")));
    }

}
