package com.epam.lab.gmail.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.epam.lab.gmail.drivers.DriverManager;

public class Button extends Element {

    public Button(WebElement webElement) {
	super(webElement);
    }

    public void click() {
	this.element.click();
    }

    public void actionClick() {
	Actions action = new Actions(DriverManager.getInstance());
	action.click(this.element).build().perform();
    }

    public void scriptClick() {
	JavascriptExecutor js = (JavascriptExecutor) DriverManager.getInstance();
	js.executeScript("arguments[0].click();", this.element);

    }
}
