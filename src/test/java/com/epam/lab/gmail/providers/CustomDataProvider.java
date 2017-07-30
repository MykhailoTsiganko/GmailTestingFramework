package com.epam.lab.gmail.providers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.testng.annotations.DataProvider;

import com.epam.lab.gmail.models.User;
import com.epam.lab.gmail.prop.csv.CSVUnmursheler;
import com.epam.lab.gmail.prop.csv.exeptions.copy.CSVUnmurshalException;

public class CustomDataProvider {
    
    @DataProvider(parallel = false)
    public Object[] getUsers() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CSVUnmurshalException, IOException {
	return CSVUnmursheler.unmurshalToList(new File("data/users.csv"), User.class).toArray();
    }

}
