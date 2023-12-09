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
}
