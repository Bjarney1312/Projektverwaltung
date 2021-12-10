package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ModuleCoordinatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleCoordinatorService {

    private ModuleCoordinatorRepository repository;

    ModuleCoordinatorService(ModuleCoordinatorRepository repository){
        this.repository = repository;
    }

    public void saveModuleCoordinator(ModuleCoordinator moduleCoordinator){
        repository.save(moduleCoordinator);
    }

    public List<ModuleCoordinator> getAllModuleCoordinators(){
        return repository.findAll();
    }

    public ModuleCoordinator getModuleCoordinatorById(Long id){
        return repository.getById(id);
    }

    public ModuleCoordinator getModuleCoordinatorByModule(ModuleEnum moduleEnum){
        return  repository.findModuleCoordinatorByModuleEnum(moduleEnum);
    }

}
