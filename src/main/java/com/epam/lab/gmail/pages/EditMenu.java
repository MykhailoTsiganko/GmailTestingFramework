package com.epam.lab.gmail.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.gmail.decorator.ElementDecorator;
import com.epam.lab.gmail.drivers.DriverSingltone;
import com.epam.lab.gmail.elements.Button;

public class EditMenu {
	private static Logger logger = Logger.getLogger(NavigationMenu.class);

	@FindBy(xpath = "//div[@class='ar9 T-I-J3 J-J5-Ji']")
	private List<Button> deleteButtons;

	public EditMenu() {
		logger.info("TopEditMenu constructor");
		PageFactory.initElements(new ElementDecorator(DriverSingltone.getInstance()), this);
	}

	public void clickDelete() {
		logger.info("delete menthod");
		for (Button deleteButton : deleteButtons) {
			if (deleteButton.isDisplayed()) {
				deleteButton.click();
			}
		}
	}

}
