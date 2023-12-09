package fr.efrei.student.server.service;

import fr.efrei.student.server.domain.Student;
import fr.efrei.student.server.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(long id) { return studentRepository.findById(id); }

    public Student saveStudent (Student student) {
        // Manual validation of my student insertion requirements
        if (student.getName() == null || student.getName().isEmpty() || student.getAge() == null || student.getAge() <= 0) {
            // Throw an exception
            throw new IllegalArgumentException("Name and age are required");
        }

        return studentRepository.save(student);
    }
}
