package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ContactPersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse ContactPersonService implementiert primär die Methoden aus dem Interface @{@link ContactPersonRepository}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author  Ramon Günther & Ivonne Kneißig
 */
@Service
public class ContactPersonService {

    private final ContactPersonRepository repository;

    public ContactPersonService(ContactPersonRepository repository) {
        this.repository = repository;
    }

    public void saveContactPerson(ContactPerson contactPerson){
        repository.save(contactPerson);
    }

    public List<ContactPerson> getAllContactPersons(){
        return repository.findAll();
    }

    public ContactPerson getContactPersonById(Long id){
        return repository.getById(id);
    }

    /**
     * Die Methode findet alle im System angelegten Ansprechpartner
     *
     * @return Liste aus Namen der vorhandenen Ansprechpartner
     */
    public List<String> getAllContactPersonNames(){
        List<ContactPerson> contacts = repository.findAll();
        List<String> contactNames = new ArrayList<>();
        for(ContactPerson contact : contacts){
            contactNames.add(contact.getLastName() + ", " + contact.getFirstName());
        }
        return contactNames;
    }

    /**
     * Die Methode findet einen Ansprechpartner anhand des Vor- & Nachnamens.
     *
     * @param name kompletter Name
     * @return Ansprechpartner
     */
    public ContactPerson getContactPersonByLastNameAndFirstName(String name){
        String[] names = name.replace(",", " ").split(" ");
        System.out.println(names[0]);
        String lastname = names[0];
        String firstname = names[2];
        try {
            ContactPerson contactPerson = repository.findContactPersonByLastNameAndFirstName(lastname, firstname);
            System.out.println(contactPerson.getCompany().getCompanyName());
            return contactPerson;
        }
        catch(Exception e){
            throw new IllegalArgumentException("Es konnte kein Ansprechpartner gefunden werden.");
        }
    }
}
