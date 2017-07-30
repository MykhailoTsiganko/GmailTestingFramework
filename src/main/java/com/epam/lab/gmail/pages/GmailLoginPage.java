package com.epam.lab.gmail.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.gmail.decorator.ElementDecorator;
import com.epam.lab.gmail.drivers.DriverSingltone;
import com.epam.lab.gmail.elements.Button;
import com.epam.lab.gmail.elements.InputText;

public class GmailLoginPage {
	public static final String LOGIN_PAGE_URL = "https://mail.google.com/";

	private Logger logger = Logger.getLogger(GmailLoginPage.class);

	@FindBy(id = "identifierId")
	private InputText loginInput;

	@FindBy(xpath = "//*[@class='ZFr60d CeoRYc']/..")
	private Button nextButton;

	@FindBy(xpath = "//*[@id='password']/div[1]/div/div[1]/input")
	private InputText passwordInput;

	@FindBy(xpath = "//*[@id='passwordNext']/div[2]")
	private Button submitButton;

	public GmailLoginPage() {
		logger.info("GmailLoginPage constructor");
		PageFactory.initElements(new ElementDecorator(DriverSingltone.getInstance()), this);
	}

	public GmailLoginPage open() {
		DriverSingltone.getInstance().get(LOGIN_PAGE_URL);
		waitForLoad();
		return this;
	}

	public void typeLoginAndSubmit(String login) {
		logger.info("typeLoginAndSubmit method");
		loginInput.sendKeys(login);
		nextButton.click();
	}

	public void typePasswordAndSubmit(String password) {
		logger.info("typePasswordAndSubmit method");
		passwordInput.sendKeys(password);
		submitButton.waitForVisability();
		submitButton.scriptClick();
	}

	private void waitForLoad() {
		new WebDriverWait(DriverSingltone.getInstance(), 30)
				.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
}
