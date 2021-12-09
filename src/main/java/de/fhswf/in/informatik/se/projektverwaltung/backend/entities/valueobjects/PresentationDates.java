package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects;


import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class PresentationDates {

	private LocalDateTime Termin1;
	private LocalDateTime Termin2;

}