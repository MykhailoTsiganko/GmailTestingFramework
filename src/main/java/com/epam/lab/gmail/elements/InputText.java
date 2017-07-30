package com.epam.lab.gmail.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.epam.lab.gmail.drivers.DriverManager;

public class InputText extends Element {

    public InputText(WebElement webElement) {
	super(webElement);
    }

    public void sendKeys(String... keys) {
	this.element.sendKeys(keys);
    }

    public void clear() {
	this.element.clear();
    }

    public void sendKeysAction(String... keys) {
	Actions action = new Actions(DriverManager.getInstance());
	action.sendKeys(this.element, keys).build().perform();
    }

}
