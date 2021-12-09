package de.fhswf.in.informatik.se.projektverwaltung.backend.repositories;


import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
