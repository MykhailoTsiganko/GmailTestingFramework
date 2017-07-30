package com.epam.lab.gmail.prop;

import java.io.FileInputStream;
import java.util.Properties;

public class DriverPropertisLoader {
	public static void load(String fileUrl) throws Exception {
		FileInputStream propFile = new FileInputStream(fileUrl);
		Properties p = new Properties(System.getProperties());
		p.load(propFile);
		System.setProperties(p);
	}
}
