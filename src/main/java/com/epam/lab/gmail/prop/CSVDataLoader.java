package com.epam.lab.gmail.prop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.epam.lab.gmail.prop.anno.CSVElement;
import com.epam.lab.gmail.prop.anno.CSVRootElement;
import com.epam.lab.gmail.prop.exeptions.CSVParsingExeption;

public class CSVDataLoader {
    
    public static <T> List<T> parse(File file,Class<T> clazz) throws CSVParsingExeption, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
	if(!clazz.isAnnotationPresent(CSVRootElement.class)) {
	    throw new CSVParsingExeption();
	}
	
	List<T> list = new ArrayList<>();
	Reader in = new FileReader(file);
	Iterable<CSVRecord> parser  = CSVFormat.EXCEL.withFirstRecordAsHeader().withRecordSeparator(",").parse(in);
	for(CSVRecord record : parser) {
	   list.add(parseElement(clazz,record));  
	}
	
	    
	return list;
    }
    

    private static <T> T parseElement(Class<T> clazz, CSVRecord record) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	
	T instance = clazz.newInstance();
	
	Method[] methods = clazz.getMethods();
	for(Method method : methods) {
	    if(method.isAnnotationPresent(CSVElement.class)) {
		String annoValue = method.getAnnotation(CSVElement.class).name();
		method.invoke(instance,method.getParameterTypes()[0].getConstructor(String.class).newInstance(annoValue));
	    }
	}
	
	Field[] fields = clazz.getDeclaredFields();
	for(Field field : fields) {
	    if(field.isAnnotationPresent(CSVElement.class)) {
		String annoValue = field.getAnnotation(CSVElement.class).name();
		field.setAccessible(true);
		field.set(instance, field.getType().getConstructor(String.class).newInstance(annoValue));
	    }
	}

	return null;
    }


    public static void main(String[] args) {
	File file = new File("data/users.csv");
	Reader in = null;

	try {
	    in = new FileReader(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Iterable<CSVRecord> parser = null;
	try {
	     parser = CSVFormat.EXCEL.withFirstRecordAsHeader().withRecordSeparator(",").parse(in);
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	for(CSVRecord record : parser) {
	    System.out.println(record.get("login"));
	    
	}

    }

}
