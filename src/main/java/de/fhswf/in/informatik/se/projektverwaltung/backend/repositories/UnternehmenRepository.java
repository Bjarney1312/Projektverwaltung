package de.fhswf.in.informatik.se.projektverwaltung.backend.repositories;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Unternehmen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnternehmenRepository extends JpaRepository<Unternehmen, Long> {

}
