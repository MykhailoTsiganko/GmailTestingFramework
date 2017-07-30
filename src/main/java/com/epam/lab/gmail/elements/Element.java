package com.epam.lab.gmail.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.gmail.drivers.DriverSingltone;

public abstract class Element {
    public final int DEFAULT_VISABILITY_TIME = 10;
    protected WebElement element;

    public Element(WebElement webElement) {
	this.element = webElement;
    }

    public String getAttribute(String attr) {
	return this.element.getAttribute(attr);
    }

    public void waitForVisability(int waitTime) {
	new WebDriverWait(DriverSingltone.getInstance(), waitTime)
		.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisability() {
	new WebDriverWait(DriverSingltone.getInstance(), DEFAULT_VISABILITY_TIME)
		.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed() {
	return this.element.isDisplayed();
    }

}
