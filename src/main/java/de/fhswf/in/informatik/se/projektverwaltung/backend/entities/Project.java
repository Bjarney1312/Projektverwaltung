package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.PresentationDates;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.ProjectDescription;

import javax.persistence.*;
import java.util.Set;

/**
 * Die Klasse Project enthält alle Informationen zu einem Projekt, das
 * ein Student zu einem Modul an der FH-Südwestfalen bearbeiten kann.
 * Dazu gehören die Projektbeschreibung, ein Ansprechpartner mit dem
 * Unternehmen zu dem er gehört, dem Modulverantwortlichen und das Modul,
 * dem das Projekt zugeordnet ist, sowie einer Gruppe von Studenten,
 * die das Projekt bearbeitet. Außerdem wird der aktuelle Status des
 * Projekts gespeichert.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	private ContactPerson contactPerson;

	@ManyToMany
	private Set<Student> students;

	@ManyToOne
	private ModuleCoordinator moduleCoordinator;

	@Embedded
	private ProjectDescription projectDescription;

	@Embedded
	private PresentationDates presentationDates;

	private Status status;
	private String comment;
	private String module;

	public Project(ContactPerson contactPerson, Set<Student> students, ModuleCoordinator moduleCoordinator, ProjectDescription projectDescription, Status status, String comment, ModuleEnum module) {
		if(students.size() <= 1 || students.size() > 3 ) {
			throw new IllegalArgumentException("Fehler: Die Gruppengröße muss im Bereich 2-3 liegen");
		}
		this.contactPerson = contactPerson;
		this.students = students;
		this.moduleCoordinator = moduleCoordinator;
		this.projectDescription = projectDescription;
		this.presentationDates = new PresentationDates(null,null);
		this.status = status;
		this.comment = comment;
		this.module = module.label;
	}

	public Project(ModuleEnum module, ModuleCoordinator moduleCoordinator) {
		this.module = module.label;
		this.presentationDates = new PresentationDates(null,null);
		this.projectDescription = new ProjectDescription();
		this.status = Status.FREI;
		this.moduleCoordinator = moduleCoordinator;
	}

	public Project() {
	}

	public Long getId() {
		return Id;
	}

	public ContactPerson getContactPerson() {
		return contactPerson;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public ModuleCoordinator getModuleCoordinator() {
		return moduleCoordinator;
	}

	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	public PresentationDates getPresentationDates() {
		return presentationDates;
	}

	public Status getStatus() {
		return status;
	}

	public String getComment() {
		return comment;
	}

	public String getModule() {
		return module;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setContactPerson(ContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void setModuleCoordinator(ModuleCoordinator moduleCoordinator) {
		this.moduleCoordinator = moduleCoordinator;
	}

	public void setProjectDescription(ProjectDescription projectDescription) {
		this.projectDescription = projectDescription;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setModule(ModuleEnum moduleEnum) {
		this.module = moduleEnum.label;
	}
}