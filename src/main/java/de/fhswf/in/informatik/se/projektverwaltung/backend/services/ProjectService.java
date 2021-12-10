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

@Service
public class ProjectService {

    private ProjectRepository repository;

    private ModuleCoordinatorRepository moduleCoordinatorRepository;

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

    public List<Student> getStudents(Long id){

        Set<Student> studentSet = repository.findById(id).get().getStudents();

        return new ArrayList<>(studentSet);
    }

    public void initializeProjects(ModuleEnum moduleEnum, int anzahl){
        ModuleCoordinatorService moduleCoordinatorService = new ModuleCoordinatorService(moduleCoordinatorRepository);
        ModuleCoordinator moduleCoordinator = moduleCoordinatorService.getModuleCoordinatorByModule(moduleEnum);
        for(int i =0 ; i < anzahl; i++){
            repository.save(new Project(moduleEnum, moduleCoordinator));
        }
    }

    public List<Project> getAllProjectsByStudents(Student student){
        return repository.getAllByStudents(student);
    }


    public Project getProjectByStatusAndModuleEnum(Status status, String moduleEnum){
        List<Project> projects = repository.getAllByStatusAndModuleEnum(status, moduleEnum);
        if(projects.isEmpty()){
            throw new IllegalArgumentException("Es ist kein freier Projektplatz verfügbar.");
        }
        return projects.get(0);
    }

}
