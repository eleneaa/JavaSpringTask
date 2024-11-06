package com.example.mod2.event;

import com.example.mod2.model.Student;
import org.springframework.context.ApplicationEvent;

public class StudentCreationEvent extends ApplicationEvent {

    private Student student;

    public StudentCreationEvent(Object source, Student student) {
        super(source);
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}

