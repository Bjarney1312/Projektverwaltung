package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.*;

/**
 * Die Klasse ContactPerson enthält alle Informationen zu einem Ansprechpartner
 * zu dem Projekt eines Studenten. Es werden Name, Mail und das Unternehmen gespeichert,
 * zu dem der ansprechpartner gehört.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Entity
public class ContactPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	@ManyToOne
	private Company company;

	private String email;

	public ContactPerson(String FirstName, String LastName, Company company, String email, String phoneNumber) {
		this.firstName = FirstName;
		this.lastName = LastName;
		this.company = company;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public ContactPerson() {

	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Company getCompany() {
		return company;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}