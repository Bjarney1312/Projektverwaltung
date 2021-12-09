package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ContactPersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactPersonService {

    private ContactPersonRepository repository;

    public ContactPersonService(ContactPersonRepository repository) {
        this.repository = repository;
    }

    public void saveContactPerson(ContactPerson contactPerson){
        repository.save(contactPerson);
    }

    public List<ContactPerson> getAllContactPersons(){
        return repository.findAll();
    }

    public ContactPerson getContactPerson(Long id){
        return repository.getById(id);
    }
}
