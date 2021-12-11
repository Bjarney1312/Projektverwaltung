package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums;

/**
 * Die Enumeration Status enthält die Statusarten, die ein Projekt im Verlauf der
 * Projektbeantragung annehmen kann.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
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