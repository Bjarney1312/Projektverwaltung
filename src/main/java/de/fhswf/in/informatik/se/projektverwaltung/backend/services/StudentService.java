package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Die Klasse StudentService implementiert primär die Methoden aus dem Interface @{@link StudentService}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author  Ramon Günther & Ivonne Kneißig
 */
@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public void saveStudent(Student student){
        repository.save(student);
    }

    public List<Student> getAllStudents(){
        return repository.findAll();
    }

    public Student getStudentById(Long id){
        return repository.getById(id);
    }

    /**
     * Die Methode findet einen Studenten anhand seines Benutzernamens.
     *
     * @param username Benutzername im System
     * @return Student
     */
    public Student getStudentByUsername(String username){
        try {
            return  repository.findStudentByUsername(username);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Es konnte kein Nutzer mit diesem Nutzernamen gefunden werden.");
        }
    }

    /**
     * Die Methode findet einen Studenten anhand seiner Fh-Mail.
     *
     * @param fhMail Fh-Mail des Studenten
     * @return Student
     */
    public Student getStudentByFhMail(String fhMail){

        Student student = repository.findStudentByFhMail(fhMail);

        if(student == null){
            throw new IllegalArgumentException("Es konnte kein Nutzer mit dieser FH-Mail gefunden werden.");
        }
        return  student;
    }

}
