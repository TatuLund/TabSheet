package org.vaadin.addons.tatu;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;

public class UserFormIT extends AbstractViewTest {

    public UserFormIT() {
        super("userform");
    }

    @Test
    public void comboBoxNavigationTest() {
        TextFieldElement combo = $(TextFieldElement.class).id("firstName");
    }
}
