package de.fhswf.in.informatik.se.projektverwaltung.backend.services;


import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public void saveProject(Project project){
        repository.save(project);
    }

    public List<Project> getAllProjects(){
        return repository.findAll();
    }

    public Project getProjectById (Long id){
        return repository.getById(id);
    }

    public List<Student> getStudents(Long id){

        Set<Student> studentSet = repository.findById(id).get().getStudents();

        return new ArrayList<>(studentSet);
    }

    public void initializeProjects(ModuleEnum moduleEnum, int anzahl){
        for(int i =0 ; i < anzahl; i++){
            repository.save(new Project(moduleEnum));
        }
    }

}
