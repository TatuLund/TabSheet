package org.vaadin.addons.tatu;

import org.junit.Assert;
import org.junit.Test;
import org.vaadin.addons.tatu.TabSheet.TabSheetVariant;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.dom.ThemeList;

public class TabSheetTest {

    @Test
    public void addTabWithIcon() {
        Div component = new Div();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab("Caption", component, VaadinIcon.VAADIN_H);
        Assert.assertTrue(
                tabSheet.getChildren().anyMatch(child -> child == component));
        Assert.assertTrue(tabSheet.getChildren()
                .anyMatch(child -> child.getElement().getAttribute("tabicon")
                        .equals("vaadin:vaadin-h")));
        Assert.assertTrue(tabSheet.getChildren()
                .anyMatch(child -> child.getElement().getAttribute("tabcaption")
                        .equals("Caption")));
        Assert.assertTrue(tabSheet.getComponent(tabSheet.getTab(0)).isPresent());
    }

    @Test
    public void addTabWithoutIcon() {
        Div component = new Div();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab("Caption", component);
        Assert.assertTrue(
                tabSheet.getChildren().anyMatch(child -> child == component));
        Assert.assertTrue(tabSheet.getChildren()
                .allMatch(child -> child.getElement().getAttribute("tabicon") == null));
        Assert.assertTrue(tabSheet.getChildren()
                .anyMatch(child -> child.getElement().getAttribute("tabcaption")
                        .equals("Caption")));
        Assert.assertTrue(tabSheet.getComponent("sheet0").isPresent());
    }

    @Test
    public void getTabFallback() {
        TabSheet tabSheet = new TabSheet();
        Assert.assertEquals("tab3",tabSheet.getTab(3));
    }
    
    @Test
    public void addRemoveByComponent() {
        Div component = new Div();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab("Caption", component);
        tabSheet.removeTab(component);
        Assert.assertEquals(0, tabSheet.getChildren().count());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeByNonChildComponentThrows() {
        Div component = new Div();
        TabSheet tabSheet = new TabSheet();
        tabSheet.removeTab(component);
    }

    @Test(expected = NullPointerException.class)
    public void removeByNullComponentThrows() {
        Div component = null;
        TabSheet tabSheet = new TabSheet();
        tabSheet.removeTab(component);
    }

    @Test(expected = NullPointerException.class)
    public void removeByNullTabThrows() {
        String tab = null;
        TabSheet tabSheet = new TabSheet();
        tabSheet.removeTab(tab);
    }

    @Test
    public void addRemoveByTab() {
        Div component = new Div();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab("Caption", component);
        tabSheet.removeTab("sheet0");
        Assert.assertEquals(0, tabSheet.getChildren().count());
    }

    @Test
    public void selectedPropertySet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSelectedIndex(5);
        Assert.assertEquals("5",
                tabSheet.getElement().getProperty("selected"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSelectedPropertyNegativeThrows() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSelectedIndex(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTabNegativeThrows() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.getTab(-5);
    }

    @Test(expected = NullPointerException.class)
    public void addTabNullThrows() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(null, null);
    }

    @Test
    public void orientationAttributeSet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setOrientation(Orientation.VERTICAL);
        Assert.assertEquals("vertical",
                tabSheet.getElement().getAttribute("orientation"));
        tabSheet.setOrientation(Orientation.HORIZONTAL);
        Assert.assertEquals("horizontal",
                tabSheet.getElement().getAttribute("orientation"));
    }

    @Test
    public void addThemeVariant_themeNamesContainsThemeVariant() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.BORDERED);

        ThemeList themeNames = tabSheet.getThemeNames();
        Assert.assertTrue(
                themeNames.contains(TabSheetVariant.BORDERED.getVariantName()));
    }

    @Test
    public void addThemeVariant_removeThemeVariant_themeNamesDoesNotContainThemeVariant() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_SMALL);
        tabSheet.addThemeVariants(TabSheetVariant.BORDERED);
        ThemeList themeNames = tabSheet.getThemeNames();
        Assert.assertTrue(themeNames
                .contains(TabSheetVariant.LUMO_SMALL.getVariantName()));
        themeNames = tabSheet.getThemeNames();
        Assert.assertTrue(
                themeNames.contains(TabSheetVariant.BORDERED.getVariantName()));
        tabSheet.removeThemeVariants(TabSheetVariant.LUMO_SMALL);

        themeNames = tabSheet.getThemeNames();
        Assert.assertFalse(themeNames
                .contains(TabSheetVariant.LUMO_SMALL.getVariantName()));
        Assert.assertTrue(
                themeNames.contains(TabSheetVariant.BORDERED.getVariantName()));
    }
}
