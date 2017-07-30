package com.epam.lab.gmail.drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSingltone {
	private static Logger logger = Logger.getLogger(DriverSingltone.class);
	
	private static List<WebDriver> driverPool = new ArrayList<>();
	private static ThreadLocal<WebDriver> thredLocaInstace = new ThreadLocal<WebDriver>();

	public static  WebDriver getInstance() {
		logger.info("getInstance method");

		if (null == thredLocaInstace.get()) {
			WebDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(10, TimeUnit.SECONDS);
			driverPool.add(driver);
			thredLocaInstace.set(driver);
		}
		return thredLocaInstace.get();
	}

	public static List<WebDriver> getDriversPool() {
		logger.info("getDriversPool method");
		return driverPool;
	}

}
