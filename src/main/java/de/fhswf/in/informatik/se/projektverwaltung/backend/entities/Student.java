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

	@ManyToMany(mappedBy = "students")
	private Set<Project> projects;

	public Student(String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student() {

	}

	public Long getId() {
		return id;
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

	public Set<Project> getProjects() {
		return projects;
	}
}