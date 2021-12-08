package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;


import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Praesentation {

	private LocalDateTime Termin1;
	private LocalDateTime Termin2;

}