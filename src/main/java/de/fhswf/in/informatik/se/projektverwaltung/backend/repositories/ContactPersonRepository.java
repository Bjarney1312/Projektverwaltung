package de.fhswf.in.informatik.se.projektverwaltung.backend.repositories;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPersonRepository extends JpaRepository<ContactPerson,Long> {
}
