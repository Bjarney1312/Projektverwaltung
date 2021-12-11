package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects;


import org.hibernate.annotations.Formula;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Die Klasse PresentationDates speichert die Präsentationstermine zu einem Projekt.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Embeddable
public class PresentationDates {

    private LocalDateTime termin1;

    private LocalDateTime termin2;

	//Damit die Embeddable Klasse kein NULL zurückwirft, wenn man was eintragen möchte
	@Formula("0")
	private int dummy;

    public PresentationDates(LocalDateTime termin1, LocalDateTime termin2) {
        this.termin1 = termin1;
        this.termin2 = termin2;
    }

    public PresentationDates() {
        termin1 = null;
        termin2 = null;
    }

    public LocalDateTime getTermin1() {
        return termin1;
    }

    public LocalDateTime getTermin2() {
        return termin2;
    }

    public void setTermin1(LocalDateTime termin1) {
        this.termin1 = termin1;
    }

    public void setTermin2(LocalDateTime termin2) {
        this.termin2 = termin2;
    }
}