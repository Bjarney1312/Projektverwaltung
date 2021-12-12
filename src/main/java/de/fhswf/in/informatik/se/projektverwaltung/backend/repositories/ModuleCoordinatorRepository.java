package de.fhswf.in.informatik.se.projektverwaltung.backend.repositories;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleCoordinatorRepository extends JpaRepository<ModuleCoordinator, Long> {
    ModuleCoordinator findModuleCoordinatorByModuleEnum(ModuleEnum modulname);
    ModuleCoordinator findByUsername(String username);

}
