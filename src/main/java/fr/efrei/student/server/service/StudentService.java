package fr.efrei.student.server.service;

import fr.efrei.student.server.domain.Student;
import fr.efrei.student.server.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;


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

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            //In the following lines, I will use Permutation to update the student
            Student studentToUpdate = existingStudent.get();

            // Check if the 'name' field is present in the updated student
            if (updatedStudent.getName() != null) {
                // Update the 'name' field
                studentToUpdate.setName(updatedStudent.getName());
            }

            // Check if the 'age' field is present in the updated student
            if (updatedStudent.getAge() != null) {
                // Update the 'age' field
                studentToUpdate.setAge(updatedStudent.getAge());
            }

            // Save the updated student
            return studentRepository.save(studentToUpdate);
        } else {
            // Throw an exception if the student is not found
            throw new EntityNotFoundException("Student not found with ID: " + id);
        }

    }

}
