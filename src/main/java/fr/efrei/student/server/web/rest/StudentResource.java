package fr.efrei.student.server.web.rest;

import fr.efrei.student.server.domain.Student;
import fr.efrei.student.server.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentResource {

    public final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public List<Student> getAllStudents(){

        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        // Use the StudentService to retrieve the student by ID
        Optional<Student> student = studentService.findById(id);

        // Check if the student exists
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            // Return 404 Not Found if the student is not found
            return ResponseEntity.notFound().build();
        }
    }

}
