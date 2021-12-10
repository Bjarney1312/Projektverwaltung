package de.fhswf.in.informatik.se.projektverwaltung;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.PresentationDates;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.ProjectDescription;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * TODO:
 *          - ProjectfindbyStudent
 *          - setter sind anscheinend schlecht? sollen die Klassen selber machen also mit new Arbeiten?
 *          - Email angabe über Vaadin validieren?
 *          - Methoden schöner machen mehr mit den Service KLassen machen um es leichter zu machen in der Benutzung?
 *          - Validierung der Eingaben
 *          - Kommentare
 */
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

        moduleCoordinatorRepository.save(new ModuleCoordinator("alnie001", "Alina","Nieswand", moduleEnums));

        //Projektplätzevergeben
        ProjectService projectService = new ProjectService(projectRepository,moduleCoordinatorRepository);
        projectService.initializeProjects(ModuleEnum.SOFTWARE_ENGINEERING, 10);

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
    }
}
