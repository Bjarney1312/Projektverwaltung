package de.fhswf.in.informatik.se.projektverwaltung;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.ProjectDescription;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
public class ProjektverwaltungApplication {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModuleCoordinatorRepository moduleCoordinatorRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjektverwaltungApplication.class, args);
    }


    @PostConstruct
    public void test(){

        studentRepository.save(new Student("ivkne001", "Ivonne ","Kneißig", "kneissig.ivonne@fh-swf.de"));
        studentRepository.save(new Student("rague002", "Ramon","Günther", "guenther.ramonantonio@fh-swf.de"));
        studentRepository.save(new Student("mapet003", "Maren","Peterson", "peterson.marenlea@fh-swf.de"));

        companyRepository.save(new Company("R & I","Im Wiesengrund","12",58636,"Iserlohn"));
        companyRepository.save(new Company("Fachhochschule Südwestfalen","Frauenstuhlweg","31",58644,"Iserlohn"));

        contactPersonRepository.save(new ContactPerson( "Jan-Hendrik","Moritz",companyRepository.getById(1L), "coolemail@krassemail.de"));
        contactPersonRepository.save(new ContactPerson( "Tobias","Holke",companyRepository.getById(2L), "coolemail@krassemail.de"));
        contactPersonRepository.save(new ContactPerson( "Matthias","Faulstich",companyRepository.getById(2L), "coolemail@krassemail.de"));

        List<ModuleEnum> moduleEnums = new ArrayList<>();
        moduleEnums.add(ModuleEnum.SOFTWARE_ENGINEERING);
        moduleEnums.add(ModuleEnum.MOBILE_APPLIKATIONEN);

        moduleCoordinatorRepository.save(new ModuleCoordinator("alnie001", "Alina","Nieswand", moduleEnums));

        moduleEnums.clear();
        moduleEnums.add(ModuleEnum.FORTGESCHRITTENE_INTERNETTECHNOLOGIEN);
        moduleCoordinatorRepository.save(new ModuleCoordinator("chgaw001", "Christian","Gawron", moduleEnums));


        //Projektplätzevergeben
        ProjectService projectService = new ProjectService(projectRepository,moduleCoordinatorRepository);
        projectService.initializeProjects(ModuleEnum.SOFTWARE_ENGINEERING, 10);
        projectService.initializeProjects(ModuleEnum.MOBILE_APPLIKATIONEN, 5);


        Project project = projectRepository.getById(1L); //TODO besser findbyid nutzen wegen optional und dem Fehler

        //Gruppe hinzufügen
        Student student1 = studentRepository.getById(1L);
        Student student2 = studentRepository.getById(2L);

        List<Student> students = new ArrayList<>();

        students.add(student1);
        students.add(student2);

        Set<Student> studentSet = new HashSet<>(students);

        project.setStudents(studentSet);


        //Modulbeauftragter

        ModuleCoordinator moduleCoordinator = moduleCoordinatorRepository.getById(1L);
        project.setModuleCoordinator(moduleCoordinator);

        //Ansprechpartner
        ContactPerson contactPerson = contactPersonRepository.getById(1L);
        project.setContactPerson(contactPerson);

        //Projektbeschreibung
        ProjectDescription projectDescription = new ProjectDescription("Mitfahrgelegenheit","Krasse Skizze","Krasse Beschreibung",null);

        project.setProjectDescription(projectDescription);

        project.setStatus(Status.ANFRAGE);


        //Projektdaten einzeln eintragen ! alle setter fertig machen wegen neue System

        projectRepository.save(project);


        Project project1 = projectService.getEmptyProject(ModuleEnum.MOBILE_APPLIKATIONEN.label);

        project1.setStudents(studentSet);


        project1.setModuleCoordinator(moduleCoordinator);

        ContactPerson contactPerson1 = contactPersonRepository.getById(2L);
        project1.setContactPerson(contactPerson1);

        project1.setProjectDescription(projectDescription);
        project1.setStatus(Status.ERGAENZUNG);

        projectRepository.save(project1);



        Project project2 = projectService.getEmptyProject(ModuleEnum.MOBILE_APPLIKATIONEN.label);

        project2.setStudents(studentSet);

        project2.setModuleCoordinator(moduleCoordinator);

        ContactPerson contactPerson2 = contactPersonRepository.getById(3L);
        project2.setContactPerson(contactPerson2);

        project2.setProjectDescription(projectDescription);
        project2.setStatus(Status.ZUGELASSEN);

        projectRepository.save(project2);

    }
}
