package com.epam.lab.gmail.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.epam.lab.gmail.prop.anno.CSVElement;
import com.epam.lab.gmail.prop.anno.CSVRootElement;


@XmlRootElement(name = "user")
@CSVRootElement
public class User {
	private String login;
	private String password;

	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public User() {
	}

	public String getLogin() {
		return login;
	}

	@XmlElement(name = "login")
	@CSVElement(name = "login")
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + "]";
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	@CSVElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

}
