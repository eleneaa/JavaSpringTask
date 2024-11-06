package com.example.mod2.event;

import com.example.mod2.model.Student;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventsListener {

    @EventListener
    public void handleStudentCreation(StudentCreationEvent event) {
        Student createdStudent = event.getStudent();
        System.out.println("New student created: " + createdStudent);
    }

    @EventListener
    public void handleStudentDeletion(StudentDeletionEvent event) {
        Long deletedStudentId = event.getStudentId();
        System.out.println("Student deleted with id: " + deletedStudentId);
    }
}

