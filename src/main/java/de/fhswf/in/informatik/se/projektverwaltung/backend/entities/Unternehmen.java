package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Unternehmen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String strasse;
	private String hausnummer;
	private int postleitzahl;
	private String ort;

	public Unternehmen(String name, String strasse, String hausnummer, int postleitzahl, String ort) {
		this.name = name;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.postleitzahl = postleitzahl;
		this.ort = ort;
	}

	public Unternehmen() {
	}

	public String getName() {
		return name;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public int getPostleitzahl() {
		return postleitzahl;
	}

	public String getOrt() {
		return ort;
	}
}