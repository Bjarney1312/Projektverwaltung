package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums;


public enum ModuleEnum {

	SOFTWARE_ENGINEERING ("Software Engineering"),
	FORTGESCHRITTENE_INTERNETTECHNOLOGIEN ("Fortgeschrittene Internettechnologien"),
	MOBILE_APPLIKATIONEN("Mobile Applikation");

	public final String label;

	ModuleEnum(String label) {
		this.label = label;
	}
}