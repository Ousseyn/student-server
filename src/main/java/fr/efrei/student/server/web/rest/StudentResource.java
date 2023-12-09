package fr.efrei.student.server.web.rest;

import fr.efrei.student.server.domain.Student;
import fr.efrei.student.server.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;

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

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.saveStudent(student);
            return ResponseEntity.ok(createdStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student updatedStudent, @PathVariable Long id){
        try {
            Student result = studentService.updateStudent(id, updatedStudent);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        // Call the delete method in the service
        boolean deleted = studentService.deleteStudent(id);

        // Return the appropriate response based on whether the deletion was successful or not
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
