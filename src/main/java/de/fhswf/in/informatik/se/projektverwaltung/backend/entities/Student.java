package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String benutzername;
	private String vorname;
	private String nachname;

	@ManyToMany
	private Set<Projekt> projects;


	public Student(String benutzername, String vorname, String nachname) {
		this.benutzername = benutzername;
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public Student() {

	}

	public Long getId() {
		return id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}
}