package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

//TODO: ManyToMany Enum?

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;

import javax.persistence.*;
import java.util.List;

@Entity
public class ModuleCoordinator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String firstName;
	private String lastName;

	@ElementCollection
	private List<ModuleEnum> moduleEnum;

	public ModuleCoordinator(String username, String firstName, String lastName, List<ModuleEnum> moduleEnum) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.moduleEnum = moduleEnum;
	}

	public ModuleCoordinator() {

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

	public List<ModuleEnum> getModul() {
		return moduleEnum;
	}
}