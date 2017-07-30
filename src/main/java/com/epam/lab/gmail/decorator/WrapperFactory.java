package com.epam.lab.gmail.decorator;

import org.openqa.selenium.WebElement;

import com.epam.lab.gmail.elements.Element;

public class WrapperFactory {

	public static Element createInstance(Class<Element> clazz, WebElement element) {
		try {
			return clazz.getConstructor(WebElement.class).newInstance(element);
		} catch (Exception e) {
			throw new AssertionError("WebElement can't be represented as " + clazz);
		}
	}
}