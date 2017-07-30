package com.epam.lab.gmail.prop;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.epam.lab.gmail.models.Order;
import com.epam.lab.gmail.prop.anno.CSVElement;
import com.epam.lab.gmail.prop.anno.CSVRootElement;
import com.epam.lab.gmail.prop.exeptions.CSVParsingExeption;

public class CSVUnmursheler {

    public static <T> List<T> unmurshalToList(File file, Class<T> clazz)
	    throws CSVParsingExeption, IOException, InstantiationException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	if (!clazz.isAnnotationPresent(CSVRootElement.class)) {
	    throw new CSVParsingExeption();
	}
	List<T> list = new ArrayList<>();
	Reader in = new FileReader(file);
	Iterable<CSVRecord> parser = CSVFormat.EXCEL.withFirstRecordAsHeader().withRecordSeparator(",")
		.parse(in);
	for (CSVRecord record : parser) {
	    list.add(parseElement(clazz, record));
	}
	return list;
    }
    
    public static <T> List<T> unmurshal(File file, Class<T> clazz,CSVFormat format)
	    throws CSVParsingExeption, IOException, InstantiationException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	if (!clazz.isAnnotationPresent(CSVRootElement.class)) {
	    throw new CSVParsingExeption();
	}
	List<T> list = new ArrayList<>();
	Reader in = new FileReader(file);
	Iterable<CSVRecord> parser = format.parse(in);
	for (CSVRecord record : parser) {
	    list.add(parseElement(clazz, record));
	}
	return list;
    }

    private static <T> T parseElement(Class<T> clazz, CSVRecord record)
	    throws InstantiationException, IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException, NoSuchMethodException, SecurityException {
	T instance = clazz.newInstance();
	Method[] methods = clazz.getMethods();
	Field[] fields = clazz.getDeclaredFields();
	
	for (Method method : methods) {
	    if (method.isAnnotationPresent(CSVElement.class)) {
		String annoValue = method.getAnnotation(CSVElement.class).name();
		String value = record.get(annoValue);
		invokeMethod(method, instance, value);
	    }
	}
	for (Field field : fields) {
	    field.setAccessible(true);
	    if (field.isAnnotationPresent(CSVElement.class)) {
		String annoValue = field.getAnnotation(CSVElement.class).name();
		String value = record.get(annoValue);
		setField(field, instance, value);
	    }
	}
	
	return instance;
    }

    private static <T> void invokeMethod(Method method, T instance, String value)
	    throws NumberFormatException, IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
	Class<?> clazz = method.getParameterTypes()[0];
	if (clazz.isPrimitive()) {
	    if (clazz.equals(byte.class)) {
		method.invoke(instance, Byte.parseByte(value));
	    } else if (clazz.equals(short.class)) {
		method.invoke(instance, Short.parseShort(value));
	    } else if (clazz.equals(int.class)) {
		method.invoke(instance, Integer.parseInt(value));
	    } else if (clazz.equals(long.class)) {
		method.invoke(instance, Long.parseLong(value));
	    } else if (clazz.equals(float.class)) {
		method.invoke(instance, Float.parseFloat(value));
	    } else if (clazz.equals(double.class)) {
		method.invoke(instance, Double.parseDouble(value));
	    } else if (clazz.equals(boolean.class)) {
		method.invoke(instance, Boolean.parseBoolean(value));
	    }
	} else {
	    method.invoke(instance, clazz.getConstructor(String.class).newInstance(value));
	}
    }

    private static <T> void setField(Field field, T instance, String value)
	    throws NumberFormatException, IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
	Class<?> clazz = field.getType();
	if (clazz.isPrimitive()) {
	    if (clazz.equals(byte.class)) {
		field.set(instance, Byte.parseByte(value));
	    } else if (clazz.equals(short.class)) {
		field.set(instance, Short.parseShort(value));
	    } else if (clazz.equals(int.class)) {
		field.set(instance, Integer.parseInt(value));
	    } else if (clazz.equals(long.class)) {
		field.set(instance, Long.parseLong(value));
	    } else if (clazz.equals(float.class)) {
		field.set(instance, Float.parseFloat(value));
	    } else if (clazz.equals(double.class)) {
		field.set(instance, Double.parseDouble(value));
	    } else if (clazz.equals(boolean.class)) {
		field.set(instance, Boolean.parseBoolean(value));
	    }
	} else {
	    field.set(instance, clazz.getConstructor(String.class).newInstance(value));
	}
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
	    CSVParsingExeption, IOException, ParseException {
	File file = new File("data/test2.csv");
	List<Order> list = unmurshal(file, Order.class,CSVFormat.DEFAULT.withHeader("id","power","isHevy","name").withRecordSeparator(","));
	list.forEach(System.out::println);
    }

}
