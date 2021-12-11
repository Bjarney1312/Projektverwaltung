package de.fhswf.in.informatik.se.projektverwaltung.backend.services;


import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ModuleCoordinatorRepository;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Die Klasse ProjectService implementiert primär die Methoden aus dem Interface @{@link ProjectRepository}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author  Ramon Günther & Ivonne Kneißig
 */
@Service
public class ProjectService {

    private final ProjectRepository repository;

    private final ModuleCoordinatorRepository moduleCoordinatorRepository;

    public ProjectService(ProjectRepository repository, ModuleCoordinatorRepository moduleCoordinatorRepository) {
        this.repository = repository;
        this.moduleCoordinatorRepository = moduleCoordinatorRepository;
    }

    public void saveProject(Project project){
        repository.save(project);
    }

    public List<Project> getAllProjects(){
        return repository.findAll();
    }

    public Optional<Project> findProjectById (Long id){
        return repository.findById(id);
    }

    /**
     * Findet alle beteiligten Studenten in dem gesuchten Projekt.
     *
     * @param id des Projekts
     * @return eine Liste der Studenten
     */
    public List<Student> getStudents(Long id){

        Set<Student> studentSet = repository.findById(id).get().getStudents();

        return new ArrayList<>(studentSet);
    }


    /**
     * Die Methode legt eine variable Anzahl von leeren Projekten zum
     * gewünschten Modul an.
     *
     * @param moduleEnum Gewünschtes Modul
     * @param anzahl Gewünschte Anzahl
     */
    public void initializeProjects(ModuleEnum moduleEnum, int anzahl){
        ModuleCoordinatorService moduleCoordinatorService = new ModuleCoordinatorService(moduleCoordinatorRepository);
        ModuleCoordinator moduleCoordinator = moduleCoordinatorService.getModuleCoordinatorByModule(moduleEnum);
        for(int i =0 ; i < anzahl; i++){
            repository.save(new Project(moduleEnum, moduleCoordinator));
        }
    }

    /**
     * Die Methode gibt eine Liste der verschiedenen Projekte zurück, zu denen der gegebene Student gehört.
     *
     * @param student Student
     * @return Liste von Projekten
     */
    public List<Project> getAllProjectsByStudents(Student student){
        return repository.getAllByStudents(student);
    }

    /**
     * Die Methode findet das erste Projekt das dem Projektstatus und dem gegebenen
     * Modul entspricht.
     *
     * @param status Status
     * @param moduleEnum Modul
     * @return Projekt
     */
    public Project getProjectByStatusAndModuleEnum(Status status, String moduleEnum){
        List<Project> projects = repository.getAllByStatusAndModuleEnum(status, moduleEnum);
        if(projects.isEmpty()){
            throw new IllegalArgumentException("Es ist kein freier Projektplatz verfügbar.");
        }
        return projects.get(0);
    }

}
