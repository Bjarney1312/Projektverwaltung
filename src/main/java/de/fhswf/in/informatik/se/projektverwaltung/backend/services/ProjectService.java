package de.fhswf.in.informatik.se.projektverwaltung.backend.services;


import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
