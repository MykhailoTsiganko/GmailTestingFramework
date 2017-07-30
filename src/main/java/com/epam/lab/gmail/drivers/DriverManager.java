package com.epam.lab.gmail.drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    private static Logger logger = Logger.getLogger(DriverManager.class);

    private static List<WebDriver> driverPool = new ArrayList<>();
    private static ThreadLocal<WebDriver> thredLocalInstace = new ThreadLocal<WebDriver>();

    public static WebDriver getInstance() {
	logger.info("getInstance method");

	if (null == thredLocalInstace.get()) {
	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(10,
		    TimeUnit.SECONDS);
	    driverPool.add(driver);
	    thredLocalInstace.set(driver);
	}
	return thredLocalInstace.get();
    }

    public static List<WebDriver> getDriversPool() {
	logger.info("getDriversPool method");
	return driverPool;
    }

    public static void closeDriver() {
	thredLocalInstace.get().quit();
	thredLocalInstace.set(null);
    }

}
