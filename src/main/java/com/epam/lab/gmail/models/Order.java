package com.epam.lab.gmail.models;

import com.epam.lab.gmail.prop.anno.CSVElement;
import com.epam.lab.gmail.prop.anno.CSVRootElement;

@CSVRootElement
public class Order {
    @CSVElement(name = "id")
    private int id;

    private double power;

    private boolean isHevy;

    private String name;

    public Order() {
	super();
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    @CSVElement(name = "id")
    public void setId(int id) {
	this.id = id;
    }

    public double getPower() {
	return power;
    }

    @CSVElement(name = "power")
    public void setPower(double power) {
	this.power = power;
    }

    public boolean isHevy() {
	return isHevy;
    }

    @CSVElement(name = "isHevy")
    public void setHevy(boolean isHevy) {
	this.isHevy = isHevy;
    }

    public String getName() {
	return name;
    }

    @CSVElement(name = "name")
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "Order [id=" + id + ", power=" + power + ", isHevy=" + isHevy + ", name=" + name + "]";
    }

}
