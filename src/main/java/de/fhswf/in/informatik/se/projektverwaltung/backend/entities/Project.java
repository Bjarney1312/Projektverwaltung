package de.fhswf.in.informatik.se.projektverwaltung.backend.entities;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.PresentationDates;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.ProjectDescription;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	private ContactPerson contactPerson;

	//TODO: Absicherung über size wegen 1-3??
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
	private ModuleEnum moduleEnum;

	public Project(ContactPerson contactPerson, Set<Student> students, ModuleCoordinator moduleCoordinator, ProjectDescription projectDescription, Status status, String comment, ModuleEnum moduleEnum) {
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
		this.moduleEnum = moduleEnum;
	}

	public Project(ModuleEnum moduleEnum) {
		this.moduleEnum = moduleEnum;
		this.presentationDates = new PresentationDates(null,null);
		this.projectDescription = new ProjectDescription();
		this.status = Status.FREI;
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

	public ModuleEnum getModuleEnum() {
		return moduleEnum;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPresentationDates(PresentationDates presentationDates) {
		this.presentationDates = presentationDates;
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

	public void setModuleEnum(ModuleEnum moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
}