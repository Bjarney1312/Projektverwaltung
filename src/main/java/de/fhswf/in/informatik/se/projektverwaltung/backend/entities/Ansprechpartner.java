package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.*;

@Entity
public class Ansprechpartner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String vorname;

	private String nachname;

	@ManyToOne
	private Unternehmen unternehmen;

	private String email;

	public Ansprechpartner(long id, String vorname, String nachname, Unternehmen unternehmen, String email) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.unternehmen = unternehmen;
		this.email = email;
	}

	public Ansprechpartner() {

	}

	public long getId() {
		return id;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public String getEmail() {
		return email;
	}
}