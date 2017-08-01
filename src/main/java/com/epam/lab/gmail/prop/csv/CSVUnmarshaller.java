package com.epam.lab.gmail.prop.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import com.epam.lab.gmail.prop.csv.anno.CSVElement;
import com.epam.lab.gmail.prop.csv.anno.CSVRootElement;
import com.epam.lab.gmail.prop.csv.exeptions.CSVUnmurshalException;

public class CSVUnmarshaller {
	private static Logger logger = Logger.getLogger(CSVUnmarshaller.class);

	public static <T> List<T> getObjects(File file, Class<T> clazz) throws CSVUnmurshalException {
		logger.info("getObjects");
		if (!clazz.isAnnotationPresent(CSVRootElement.class)) {
			throw new CSVUnmurshalException();
		}
		List<T> list = new ArrayList<>();
		try {
			Reader in = new FileReader(file);
			Iterable<CSVRecord> parser = CSVFormat.EXCEL.withFirstRecordAsHeader().withRecordSeparator(",").parse(in);
			for (CSVRecord record : parser) {
				list.add(getElement(clazz, record));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		return list;
	}

	public static <T> List<T> getObjects(File file, Class<T> clazz, CSVFormat format)
			throws CSVUnmurshalException, IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		logger.info("getObjects method");
		if (!clazz.isAnnotationPresent(CSVRootElement.class)) {
			throw new CSVUnmurshalException();
		}
		List<T> list = new ArrayList<>();
		try {
			Reader in = new FileReader(file);
			Iterable<CSVRecord> parser = format.parse(in);
			for (CSVRecord record : parser) {
				list.add(getElement(clazz, record));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		return list;
	}

	private static <T> T getElement(Class<T> clazz, CSVRecord record)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		logger.info("getElement");
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
			throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InstantiationException, NoSuchMethodException, SecurityException {
		logger.info("invokeMethod");
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
			throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InstantiationException, NoSuchMethodException, SecurityException {
		logger.info("setField");
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
}
