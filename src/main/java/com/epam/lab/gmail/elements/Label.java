package com.epam.lab.gmail.elements;

import org.openqa.selenium.WebElement;

public class Label extends Element{

	public Label(WebElement webElement) {
		super(webElement);
	}
	
	public String getText() {
	    return this.element.getText();
	}
	
	
	public String getContex() {
	    return this.element.getAttribute("textContent");
	}
	
	
}
