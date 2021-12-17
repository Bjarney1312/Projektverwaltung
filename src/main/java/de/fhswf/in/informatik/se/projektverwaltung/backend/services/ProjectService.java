package de.fhswf.in.informatik.se.projektverwaltung.backend.services;


import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ModuleCoordinatorRepository;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Die Klasse ProjectService implementiert primär die Methoden aus dem Interface @{@link ProjectRepository}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author Ramon Günther & Ivonne Kneißig
 */
@Service
public class ProjectService {

    private final ProjectRepository repository;

    private final ModuleCoordinatorRepository moduleCoordinatorRepository;

    public ProjectService(ProjectRepository repository, ModuleCoordinatorRepository moduleCoordinatorRepository) {
        this.repository = repository;
        this.moduleCoordinatorRepository = moduleCoordinatorRepository;
    }

    public void saveProject(Project project) {
        repository.save(project);
    }

    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public Optional<Project> findProjectById(Long id) {
        return repository.findById(id);
    }

    /**
     * Die Methode legt eine variable Anzahl von leeren Projekten zum
     * gewünschten Modul an.
     *
     * @param moduleEnum Gewünschtes Modul
     * @param anzahl     Gewünschte Anzahl
     */
    public void initializeProjects(ModuleEnum moduleEnum, int anzahl) {
        ModuleCoordinatorService moduleCoordinatorService = new ModuleCoordinatorService(moduleCoordinatorRepository);
        ModuleCoordinator moduleCoordinator = moduleCoordinatorService.getModuleCoordinatorByModule(moduleEnum);
        for (int i = 0; i < anzahl; i++) {
            repository.save(new Project(moduleEnum, moduleCoordinator));
        }
    }

    /**
     * Die Methode gibt eine Liste der verschiedenen Projekte zurück, zu denen der gegebene Student gehört.
     *
     * @param student Student
     * @return Liste von Projekten
     */
    public List<Project> getAllProjectsByStudents(Student student) {
        return repository.getAllByStudents(student);
    }

    /**
     * Die Methode findet das erste Projekt das dem Projektstatus und dem gegebenen
     * Modul entspricht.
     *
     * @param moduleEnum Modul
     * @return Projekt
     */
    public Project getEmptyProject(String moduleEnum) {
        List<Project> projects = repository.getAllByModuleAndStatus(moduleEnum, Status.FREI);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("Es ist kein freier Projektplatz verfügbar.");
        }
        return projects.get(0);
    }

    /**
     * Gibt alle Projekte eines Moduls des Modulbeauftragten, abhängig vom Status, als Liste zurück.
     *
     * @param status Status des Projekts
     * @param module gesuchtes Modul
     * @return Liste Projekte
     */
    public List<Project> getAllByStatusAndModule(Status status, String module) {
        return repository.getAllByModuleAndStatus(module, status);
    }


    /**
     * Gibt alle NICHT freien Projekte eines Moduls des Modulbeauftragten als Liste zurück
     *
     * @param module gesuchtes Modul
     * @return Liste Projekte ohne freie
     */
    public List<Project> getAllByModuleAndStatusNot(String module) {
        return repository.getAllByModuleAndStatusNot(module, Status.FREI);
    }

    /**
     * Gibt alle NICHT freien Projekte der Module des Modulbeauftragten als Liste zurück
     *
     * @param moduleCoordinator Modulbeauftragter
     * @return Liste Projekte ohne freie
     */
    public List<Project> getAllProjectsWithoutEmpty(ModuleCoordinator moduleCoordinator) {
        List<Project> projectList = new ArrayList<>();
        for (ModuleEnum moduleEnum : moduleCoordinator.getModul()) {
            projectList.addAll(repository.getAllByModuleAndStatusNot(moduleEnum.label, Status.FREI));
        }
        return projectList;
    }

    /**
     * Gibt alle Projekte der Module des Modulbeauftragten, abhängig vom Status, als Liste zurück.
     *
     * @param moduleCoordinator Modulbeauftragter
     * @param status Status des Projektes
     * @return Liste Projekte mit gewähltem Status
     */
    public List<Project> getAllProjectsByStatus(ModuleCoordinator moduleCoordinator, Status status) {
        List<Project> projectList = new ArrayList<>();
        for (ModuleEnum moduleEnum : moduleCoordinator.getModul()) {
            projectList.addAll(repository.getAllByModuleAndStatus(moduleEnum.label, status));
        }
        return projectList;
    }

    /**
     *
     * @param moduleEnum
     * @param anzahl
     */
    public void deleteFreeProjects(String moduleEnum, int anzahl) {
        List<Project> projectList = repository.getAllByModuleAndStatus(moduleEnum,Status.FREI);
        for (int i = 0; i < anzahl; i++) {
            repository.delete(projectList.get(i));
        }
    }
}



