package com.epam.lab.gmail.prop;

import java.io.File;
import java.util.Objects;
import java.util.Queue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.epam.lab.gmail.models.User;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserManager {
	public static final String USES_FILE_URL = "data/users.xml";
	private static UserManager instance;

	public static synchronized User getUser() {
		if (Objects.isNull(instance)) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(UserManager.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				instance = (UserManager) jaxbUnmarshaller.unmarshal(new File(USES_FILE_URL));
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return instance.usersList.poll();
	}

	public static Object[] getUsers() {
		return instance.usersList.toArray();

	}

	@XmlElement(name = "user")
	private Queue<User> usersList;

}
