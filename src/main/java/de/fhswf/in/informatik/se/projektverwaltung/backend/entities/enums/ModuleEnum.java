package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums;


import java.util.ArrayList;
import java.util.List;

public enum ModuleEnum {

	SOFTWARE_ENGINEERING ("Software Engineering"),
	FORTGESCHRITTENE_INTERNETTECHNOLOGIEN ("Fortgeschrittene Internettechnologien"),
	MOBILE_APPLIKATIONEN("Mobile Applikationen");

	public final String label;

	ModuleEnum(String label) {
		this.label = label;
	}


}