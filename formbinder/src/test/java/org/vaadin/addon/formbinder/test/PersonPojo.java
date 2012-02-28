package org.vaadin.addon.formbinder.test;

public class PersonPojo {
	
	private String firstName = "Matti";
	private String lastName = "Meikäläinen";
	private int age = 69;
	private String street = "Ruukinkatu";
	private String city = "Turku";
	private String zipCode  = "1234";
	
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Override
	public String toString() {
		return "PersonPojo [firstName=" + firstName + ", lastName=" + lastName
				+ ", age=" + age + ", street=" + street + ", city=" + city
				+ ", zipCode=" + zipCode + "]";
	}
	
	

	
}
