package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String fhMail;

	@ManyToMany(mappedBy = "students")
	private Set<Project> projects;

	public Student(String username, String firstName, String lastName, String fhMail) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fhMail = fhMail;
	}

	public Student() {
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFhMail() {
		return fhMail;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFhMail(String fhMail) {
		this.fhMail = fhMail;
	}
}