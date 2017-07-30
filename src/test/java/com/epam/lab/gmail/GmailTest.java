package com.epam.lab.gmail;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.gmail.bisnes_layour.GmailBO;
import com.epam.lab.gmail.bisnes_layour.LoginBO;
import com.epam.lab.gmail.drivers.DriverSingltone;
import com.epam.lab.gmail.models.Message;
import com.epam.lab.gmail.models.User;
import com.epam.lab.gmail.prop.UsersDataHolder;
import com.epam.lab.gmail.prop.PropertiesLoader;

public class GmailTest {
	public static final String PROPERTIES_FILE_URL = "resources/driver_config.properties";
	public static User user;

	public static Logger logger = Logger.getLogger(GmailTest.class);

	@BeforeClass
	public void setUp() throws Exception {
		logger.info("setUp");
		PropertiesLoader.load(PROPERTIES_FILE_URL);
	}

	@Test(threadPoolSize = 5, invocationCount = 2)
	public void markMessagesToImportantAndDelete() {

		LoginBO loginBO = new LoginBO();

		loginBO.loginAs(UsersDataHolder.getUser());

		GmailBO gmailBo = new GmailBO();

		List<Message> markedMessagesList = gmailBo.markMessagesAsImportant(3);
		
		assertNotNull(markedMessagesList);

		gmailBo.openImportantMesssagesList();

		gmailBo.deleteMessages(markedMessagesList);

		assertFalse(gmailBo.arePresent(markedMessagesList));
	}

	@AfterMethod
	public void after() {
		DriverSingltone.getInstance().quit();
	}

}
