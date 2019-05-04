package com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class FacilityDetail {

	private String facilityName;

	private int numberOfRooms;

	private int phoneNumber;



	public String getName() {
		return facilityName;
	}

	public void setName(String name) {
		this.facilityName = name;

	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;

	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int PhoneNumber) {
		this.phoneNumber = PhoneNumber;

	}

}
