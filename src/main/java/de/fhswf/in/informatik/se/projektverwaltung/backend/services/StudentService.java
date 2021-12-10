package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

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

    public Student getStudentByUsername(String username){
        try {
            return  repository.findStudentByUsername(username);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Es konnte kein Nutzer mit diesem Nutzernamen gefunden werden.");
        }
    }

    public Student getStudentByFhMail(String fhMail){

        Student student = repository.findStudentByFhMail(fhMail);

        if(student == null){
            throw new IllegalArgumentException("Es konnte kein Nutzer mit dieser FH-Mail gefunden werden.");
        }
        return  student;
    }

}
