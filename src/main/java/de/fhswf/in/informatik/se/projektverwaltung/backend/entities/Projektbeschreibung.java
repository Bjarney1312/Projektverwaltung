package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Projektbeschreibung {

	private String titel;
	private String skizze;
	private String beschreibungHintergrund;
	private byte beschreibungProjektinhalt;

}