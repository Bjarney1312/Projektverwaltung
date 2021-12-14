package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ModuleCoordinatorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Die Klasse ModuleCoordinatorService implementiert primär die Methoden aus dem Interface @{@link ModuleCoordinatorRepository}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author  Ramon Günther & Ivonne Kneißig
 */
@Service
public class ModuleCoordinatorService {

    private final ModuleCoordinatorRepository repository;

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
        return repository.findModuleCoordinatorByModuleList(moduleEnum);
    }

   public ModuleCoordinator findByUsername(){
        return repository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
   }

}
