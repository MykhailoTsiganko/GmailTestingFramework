package com.epam.lab.gmail.providers;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.epam.lab.gmail.models.User;
import com.epam.lab.gmail.prop.csv.CSVUnmarshaller;
import com.epam.lab.gmail.prop.csv.exeptions.CSVUnmurshalException;

public class CustomDataProvider {

	@DataProvider(parallel = true)
	public Object[] getUsers() throws CSVUnmurshalException {
		return CSVUnmarshaller.getObjects(new File("data/users.csv"), User.class).toArray();
	}

}
