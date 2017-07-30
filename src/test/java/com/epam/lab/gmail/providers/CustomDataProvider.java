package com.epam.lab.gmail.providers;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.epam.lab.gmail.models.User;
import com.epam.lab.gmail.prop.csv.CSVUnmursheler;
import com.epam.lab.gmail.prop.csv.exeptions.copy.CSVUnmurshalException;

public class CustomDataProvider {
    
    @DataProvider(parallel = false)
    public Object[] getUsers() throws CSVUnmurshalException {
	return CSVUnmursheler.getObjects(new File("data/users.csv"), User.class).toArray();
    }

}
