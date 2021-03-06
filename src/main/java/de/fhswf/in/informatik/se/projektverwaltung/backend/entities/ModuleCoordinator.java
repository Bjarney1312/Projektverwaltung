package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;

import javax.persistence.*;
import java.util.List;

/**
 * Die Klasse ModuleCoordinator enthält alle Informationen zu einem Dozenten bzw. Modulbeauftragen,
 * der Projekte für Studenten zu einem oder mehreren Modulen anbietet.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Entity
public class ModuleCoordinator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String firstName;
	private String lastName;

	@ElementCollection
	private List<ModuleEnum> moduleList;

	public ModuleCoordinator(String username, String firstName, String lastName, List<ModuleEnum> moduleList) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.moduleList = moduleList;
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
		return moduleList;
	}

	public ModuleEnum getModulByModulName(String modulName){
		ModuleEnum result = null;
		for(ModuleEnum module : moduleList){
			if(module.label.equals(modulName)){
				result = module;
			}
		}
		return result;
	}
}