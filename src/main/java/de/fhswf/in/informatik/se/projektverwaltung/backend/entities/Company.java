package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Die Klasse Company enthält alle Informationen zu einem Unternehmen,
 * zu dem der Ansprechpartner eines Projekts gehört. Es werden der
 * Unternehmensname und die Adresse gespeichert.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String companyName;
	private String street;
	private int postalCode;
	private String location;

	public Company(String CompanyName, String street, int postalCode, String location) {
		this.companyName = CompanyName;
		this.street = street;
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

	public int getPostalCode() {
		return postalCode;
	}

	public String getLocation() {
		return location;
	}
}