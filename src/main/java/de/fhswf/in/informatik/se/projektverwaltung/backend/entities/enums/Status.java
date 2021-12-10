package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums;


public enum Status {

	ZUGELASSEN("Zugelassen"),
	ABGELEHNT("Abgelehnt"),
	ERGAENZUNG ("Ergänzung"),
	ANFRAGE ("Anfrage"),
	FREI("Frei");

	public final String label;

	Status(String label) {
		this.label = label;
	}
}