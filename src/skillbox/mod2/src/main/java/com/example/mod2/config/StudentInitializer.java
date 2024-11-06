package com.example.mod2.config;

import com.example.mod2.model.Student;
import com.example.mod2.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StudentInitializer {

    private final StudentRepository studentRepository;
    private final StudentCreationConfig studentCreationConfig;

    @Autowired
    public StudentInitializer(StudentRepository studentRepository, StudentCreationConfig studentCreationConfig) {
        this.studentRepository = studentRepository;
        this.studentCreationConfig = studentCreationConfig;
    }

    @PostConstruct
    public void init() {
        if (studentCreationConfig.isEnabled()) {
            // Создание студентов при старте приложения
            Student student1 = Student.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .age(17)
                    .build();
            Student student2 = Student.builder()
                    .firstName("Max")
                    .lastName("Smith")
                    .age(17)
                    .build();
            studentRepository.save(student1);
            studentRepository.save(student2);
        }
    }
}

