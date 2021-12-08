package de.fhswf.in.informatik.se.projektverwaltung;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Unternehmen;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.StudentRepository;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.UnternehmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ProjektverwaltungApplication {


    @Autowired
    private StudentRepository repository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjektverwaltungApplication.class, args);
    }


    @PostConstruct
    public void test(){
        repository.save(new Student("Raytastic", "Ramon", "Günther"));

        unternehmenRepository.save(new Unternehmen("R&I Onlineshop", "Sundernallee" ,"75" ,58636, "Iserlohn"));
    }
}
