package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String companyName;
	private String street;
	private String houseNumber;
	private int postalCode;
	private String location;

	public Company(String CompanyName, String street, String houseNumber, int postalCode, String location) {
		this.companyName = CompanyName;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.location = location;
	}

	public Company() {
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public String getLocation() {
		return location;
	}
}