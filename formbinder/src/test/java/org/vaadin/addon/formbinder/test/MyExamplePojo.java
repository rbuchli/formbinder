package org.vaadin.addon.formbinder.test;

import java.util.Date;

public class MyExamplePojo {

	private String firstName;
	private String lastName;
	private Date birthDate;
	private double weight;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + ", born " + birthDate + ", "
				+ weight + "kg";
	}

}
