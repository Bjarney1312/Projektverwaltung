package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums;

/**
 * Die Enumeration ModuleEnum enthält alle Module, zu denen Projekte angeboten
 * bzw. bearbeitet werden können.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
public enum ModuleEnum {

	SOFTWARE_ENGINEERING ("Software Engineering"),
	FORTGESCHRITTENE_INTERNETTECHNOLOGIEN ("Fortgeschrittene Internettechnologien"),
	MOBILE_APPLIKATIONEN("Mobile Applikationen");

	public final String label;

	ModuleEnum(String label) {
		this.label = label;
	}


}