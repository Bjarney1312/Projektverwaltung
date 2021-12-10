package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ContactPersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactPersonService {

    private static final String ADDRESS_PATTERN = "(?<lastname>[A-Za-z_äÄöÖüÜß\\s]+) ([,] (?<firstname>A-Za-z_äÄöÖüÜß\\s]+)";

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

    public List<String> getAllContactPersonNames(){
        List<ContactPerson> contacts = repository.findAll();
        List<String> contactNames = new ArrayList<>();
        for(ContactPerson contact : contacts){
            contactNames.add(contact.getLastName() + ", " + contact.getFirstName());
        }
        return contactNames;
    }

    //TODO: Sicherer machen und so mit Regex
    public ContactPerson getContactPersonByLastNameAndFirstName(String name){
        String[] names = name.replace(",", " ").split(" ");
        System.out.println(names[0]);
        String lastname = names[0];
        String firstname = names[2];

//        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
//        Matcher addressMatcher = pattern.matcher(name.replace(",", " "));

//            addressMatcher.find();
//            lastname = addressMatcher.group("strasse").trim();
//            firstname = addressMatcher.group("hausnummer").trim();

        try { //TODO vllt gehts doch ohne try catch
            ContactPerson contactPerson = repository.findContactPersonByLastNameAndFirstName(lastname, firstname);
            System.out.println(contactPerson.getCompany().getCompanyName());
            return contactPerson;
        }
        catch(Exception e){
            throw new IllegalArgumentException("nix");
        }
    }
}
