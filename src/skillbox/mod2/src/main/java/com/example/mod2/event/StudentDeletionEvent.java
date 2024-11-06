package com.example.mod2.event;

import org.springframework.context.ApplicationEvent;

public class StudentDeletionEvent extends ApplicationEvent {

    private Long studentId;

    public StudentDeletionEvent(Object source, Long studentId) {
        super(source);
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}

