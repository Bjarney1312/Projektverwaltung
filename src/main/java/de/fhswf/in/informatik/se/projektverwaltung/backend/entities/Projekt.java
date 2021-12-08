package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Modul;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Projekt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	private Ansprechpartner ansprechpartner;

	//TODO: Absicherung über size wegen 1-3??
	@ManyToMany(mappedBy = "projects")
	private Set<Student> studenten;

	@ManyToOne
	private Dozent dozent;

	@Embedded
	private Projektbeschreibung projektbeschreibung;

	@Embedded
	private Praesentation praesentation;

	private Status status;
	private String kommentar;
	private String attribute;
	private Modul modul;

	public Projekt(Ansprechpartner ansprechpartner, Set<Student> studenten, Dozent dozent, Status status, String kommentar, String attribute, Modul modul) {
		this.ansprechpartner = ansprechpartner;
		this.studenten = studenten;
		this.dozent = dozent;
		this.status = status;
		this.kommentar = kommentar;
		this.attribute = attribute;
		this.modul = modul;
	}


	public Projekt() {
	}

	public Long getId() {
		return Id;
	}

	public Ansprechpartner getAnsprechpartner() {
		return ansprechpartner;
	}


	public Status getStatus() {
		return status;
	}

	public Dozent getDozent() {
		return dozent;
	}

	public String getKommentar() {
		return kommentar;
	}

	public String getAttribute() {
		return attribute;
	}

	public Modul getModul() {
		return modul;
	}
}