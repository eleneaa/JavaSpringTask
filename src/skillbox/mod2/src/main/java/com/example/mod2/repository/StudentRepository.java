package com.example.mod2.repository;

import com.example.mod2.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
    // Метод для добавления нового студента
    // Возвращает сохраненного студента с присвоенным идентификатором
    Student save(Student student);

    // Метод для удаления студента по идентификатору
    void deleteById(Long id);

    // Метод для получения списка всех студентов
    Iterable<Student> findAll();

    void deleteAll();
}

