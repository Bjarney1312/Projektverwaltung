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
import java.time.LocalDateTime;
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

        studentRepository.save(new Student("birnpfel57", "apfel","birne"));
        studentRepository.save(new Student("benutzer145", "Kein plan","Granatapfel"));


        for(Student student: studentRepository.findAll()){
            System.out.println(student.getLastName());
        }

        companyRepository.save(new Company("R & I","Im Wiesengrund","12",58636,"Iserlohn"));

        for(Company company: companyRepository.findAll()){
            System.out.println(company.getCompanyName());
        }



        contactPersonRepository.save(new ContactPerson( "Ivonne","Kneißig",companyRepository.getById(1L), "coolemail@krassemail.de"));

        for(ContactPerson contactPerson: contactPersonRepository.findAll()){
            System.out.println(contactPerson.getFirstName() + ", " + contactPerson.getLastName());
            System.out.println(contactPerson.getCompany().getCompanyName());
        }

        System.out.println(studentRepository.getById(1L).getId());


        List<ModuleEnum> moduleEnums = new ArrayList<>();
        moduleEnums.add(ModuleEnum.FORTGESCHRITTENE_INTERNETTECHNOLOGIEN);
        moduleEnums.add(ModuleEnum.MOBILE_APPLIKATIONEN);

        moduleCoordinatorRepository.save(new ModuleCoordinator("Alina", "Nieswand","jddd", moduleEnums));

        for(ModuleCoordinator moduleCoordinator: moduleCoordinatorRepository.findAll()){
            System.out.println(moduleCoordinator.getFirstName());
        }

        //Projekt erstellen

//        Student student1 = studentRepository.getById(1L);
//        Student student2 = studentRepository.getById(2L);
//
//        //Gruppenbildung
//
////        List<Student> students = studentRepository.findAll();
//        List<Student> students = new ArrayList<>();
//
//        students.add(student1);
//        students.add(student2);
//
//        Set<Student> studentSet = new HashSet<>(students);
//
//        ContactPerson contactPerson = contactPersonRepository.getById(1L);
//
//        ModuleCoordinator moduleCoordinator = moduleCoordinatorRepository.getById(1L);
//
//        ProjectDescription projectDescription = new ProjectDescription("Mitfahrgelegenheit","Krasse Skizze","Krasse 1-2 A4 Seiten",null);
//
//        projectRepository.save(new Project(contactPerson,studentSet,moduleCoordinator, projectDescription, Status.ERGAENZUNG,"Lorem Ipsum und so",ModuleEnum.MOBILE_APPLIKATIONEN));
//


//      So kann man sich nur die ID ausgeben sonst fliegt fehler TODO: Wenn spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true dann geht es
//        Project project = projectRepository.getById(1L);
//
//        System.out.println(projectRepository.getById(1L).getStatus().label);


        //********************************************************
//        Optional optionalProject = projectRepository.findById(1L);
//
//        Project project = (Project) optionalProject.get();
//
//        System.out.println(project.getStatus().label);
//
//        project.setStatus(Status.ZUGELASSEN);
//
//        System.out.println(project.getStatus().label);
//
//
//        //TODO für eine Variante entscheiden
//        //1
//        project.getPresentationDates().setTermin1(LocalDateTime.now());
//        System.out.println(project.getPresentationDates().getTermin1());
//
//        //2
//        project.setPresentationDates(new PresentationDates(null,LocalDateTime.now())); //Termin1 wird damit überschrieben
//        System.out.println(project.getPresentationDates().getTermin1());
//
//        projectRepository.save(project);
//
//
//        //Studenten ausgeben
//
//        //Variante 1
////        Set<Student> test = project.getStudents();
////        List<Student> studentList = new ArrayList<>(test);
//
//        //Variante 2
//        ProjectService projectService = new ProjectService(projectRepository);
//        List<Student> studentList = projectService.getStudents(1L); //TODO: Wenn spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true dann geht es, sonst nicht
//
//        //Ausgabe
//        for(Student student : studentList){
//            System.out.println(student.getUsername());
//        }

        //TODO AUSKOMMENTIEREN FÜR UPLOAD ZEIGEN


        //Projektplätzevergeben
        ProjectService projectService = new ProjectService(projectRepository);
        projectService.initializeProjects(ModuleEnum.MOBILE_APPLIKATIONEN, 10);
//
//        Project project = projectService.getProjectById(1L); //TODO besser findbyid nutzen wegen optional und dem Fehler
//
//        //Gruppe hinzufügen
//        Student student1 = studentRepository.getById(1L);
//        Student student2 = studentRepository.getById(2L);
//
//        List<Student> students = new ArrayList<>();
//
//        students.add(student1);
//        students.add(student2);
//
//        Set<Student> studentSet = new HashSet<>(students);
//
//        project.setStudents(studentSet);
//
//
//        //Modulbeauftragter
//
//        ModuleCoordinator moduleCoordinator = moduleCoordinatorRepository.getById(1L);
//        project.setModuleCoordinator(moduleCoordinator);
//
//        //Ansprechpartner
//        ContactPerson contactPerson = contactPersonRepository.getById(1L);
//        project.setContactPerson(contactPerson);
//
//        //Projektbeschreibung
//        ProjectDescription projectDescription = new ProjectDescription("Mitfahrgelegenheit","Krasse Skizze","Krasse Beschreibung",null);
//
//        project.setProjectDescription(projectDescription);
//
//
//        //Termine
//        project.getPresentationDates().setTermin1(LocalDateTime.now());
//        project.getPresentationDates().setTermin2(null);
//
//        //Projektdaten einzeln eintragen ! alle setter fertig machen wegen neue System
//
//        projectRepository.save(project);

    }
}
