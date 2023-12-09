package fr.efrei.student.server.web.rest;


import fr.efrei.student.server.domain.Student;
import fr.efrei.student.server.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

@TestPropertySource(
        locations= "classpath:application-test.properties"
)

public class StudentResourceIT {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentResource studentResource;

    @Test
    @Transactional
    void createTestStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        assertThat(databaseSizeBeforeCreate).isEqualTo(0);

        Student student = new Student();
        student.setAge(25);
        student.setName("Mamadou");
        studentRepository.save(student);

        List <Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void testStudentUpdate() {
        // Create a new student in the database
        Student initialStudent = new Student();
        initialStudent.setAge(25);
        initialStudent.setName("Initial Name");
        studentRepository.save(initialStudent);

        // Get the ID of the created student
        Long studentId = initialStudent.getId();

        // Case 1: Partial Update (Update age only)
        Student partialUpdateStudent = new Student();
        partialUpdateStudent.setAge(30);
        studentResource.updateStudent(partialUpdateStudent, studentId);

        // Check if the age is updated and name remains the same
        Student partialUpdatedStudent = studentRepository.findById(studentId).orElse(null);
        assertThat(partialUpdatedStudent).isNotNull();
        assertThat(partialUpdatedStudent.getAge()).isEqualTo(partialUpdateStudent.getAge());
        assertThat(partialUpdatedStudent.getName()).isEqualTo(initialStudent.getName());

        // Case 2: Partial Update (Update name only)
        Student partialUpdateNameStudent = new Student();
        partialUpdateNameStudent.setName("New Name");
        studentResource.updateStudent(partialUpdateNameStudent, studentId);

        // Check if the name is updated and age remains the same
        Student partialUpdatedNameStudent = studentRepository.findById(studentId).orElse(null);
        assertThat(partialUpdatedNameStudent).isNotNull();
        assertThat(partialUpdatedNameStudent.getAge()).isEqualTo(partialUpdatedStudent.getAge());
        assertThat(partialUpdatedNameStudent.getName()).isEqualTo(partialUpdateNameStudent.getName());

        // Case 3: Total Update (Update both age and name)
        Student totalUpdateStudent = new Student();
        totalUpdateStudent.setAge(35);
        totalUpdateStudent.setName("Totally New Name");
        studentResource.updateStudent(totalUpdateStudent, studentId);

        // Check if both age and name are updated
        Student totallyUpdatedStudent = studentRepository.findById(studentId).orElse(null);
        assertThat(totallyUpdatedStudent).isNotNull();
        assertThat(totallyUpdatedStudent.getAge()).isEqualTo(totalUpdateStudent.getAge());
        assertThat(totallyUpdatedStudent.getName()).isEqualTo(totalUpdateStudent.getName());

        // Case 4: Update with Null Fields (No changes should occur)
        Student nullUpdateStudent = new Student();
        studentResource.updateStudent(nullUpdateStudent, studentId);

        // Check if no changes occurred
        Student studentAfterNullUpdate = studentRepository.findById(studentId).orElse(null);
        assertThat(studentAfterNullUpdate).isNotNull();
        assertThat(studentAfterNullUpdate.getAge()).isEqualTo(totallyUpdatedStudent.getAge());
        assertThat(studentAfterNullUpdate.getName()).isEqualTo(totallyUpdatedStudent.getName());
    }

    @Test
    @Transactional
    void testDeleteStudent() {
        // Create a new student in the database
        Student student = new Student();
        student.setAge(25);
        student.setName("To Be Deleted");
        studentRepository.save(student);

        // Get the ID of the created student
        Long studentId = student.getId();

        // Delete the student
        studentResource.deleteStudent(studentId);

        // Check if the student is deleted
        assertThat(studentRepository.findById(studentId)).isEmpty();
    }
}
