package com.epam.lab.gmail.bisnes_layour;

import com.epam.lab.gmail.models.User;
import com.epam.lab.gmail.pages.GmailLoginPage;

public class LoginBO {

	private GmailLoginPage loginPage;

	public void loginAs(User user) {
		loginPage = new GmailLoginPage();
		loginPage.open();
		loginPage.typeLoginAndSubmit(user.getLogin());
		loginPage.typePasswordAndSubmit(user.getPassword());
	}

}
