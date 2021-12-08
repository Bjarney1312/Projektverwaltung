package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

//TODO: ManyToMany Enum?

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Modul;

import javax.persistence.*;
import java.util.List;

@Entity
public class Dozent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String benutzername;
	private String vorname;
	private String nachname;

	@ElementCollection
	private List<Modul> modul;

	public Dozent(String benutzername, String vorname, String nachname, List<Modul> modul) {
		this.benutzername = benutzername;
		this.vorname = vorname;
		this.nachname = nachname;
		this.modul = modul;
	}

	public Dozent() {

	}

	public Long getId() {
		return id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public List<Modul> getModul() {
		return modul;
	}
}