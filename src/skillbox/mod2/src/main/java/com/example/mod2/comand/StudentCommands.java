package com.example.mod2.comand;

import com.example.mod2.event.StudentCreationEvent;
import com.example.mod2.event.StudentDeletionEvent;
import com.example.mod2.model.Student;
import com.example.mod2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class StudentCommands {
    private StudentRepository studentRepository;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public StudentCommands(StudentRepository studentRepository, ApplicationEventPublisher eventPublisher) {
        this.studentRepository = studentRepository;
        this.eventPublisher = eventPublisher;
    }

    @ShellMethod("Добавить студента")
    public String addStudent(String firstName, String lastName, int age) {
        Student newStudent = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
        Student savedStudent = studentRepository.save(newStudent);

        // Опубликовать событие создания студента
        eventPublisher.publishEvent(new StudentCreationEvent(this, savedStudent));

        return "Студент добавлен: " + firstName + " " + lastName;
    }

    @ShellMethod("Удалить студента")
    public String deleteStudent(Long id) {
        studentRepository.deleteById(id);

        // Опубликовать событие удаления студента
        eventPublisher.publishEvent(new StudentDeletionEvent(this, id));

        return "Студент удален с ID: " + id;
    }

    @ShellMethod("Удалить всех студентов")
    public String deleteAllStudent() {
        studentRepository.deleteAll();
        return "Студенты удалены";
    }

    @ShellMethod("Показать всех студентов")
    public String showAllStudents() {
        StringBuilder result = new StringBuilder();
        Iterable<Student> students = studentRepository.findAll();

        for (Student student : students) {
            result.append(student.toString()).append("\n");
        }

        if (result.length() == 0) {
            return "Нет студентов в базе.";
        }

        return result.toString();}
}


