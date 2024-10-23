package skillbox.mod2.src.main.java.com.example.mod2.comand;

import com.example.mod2.model.Student;
import com.example.mod2.repository.StudentRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class StudentComands {
    private StudentRepository studentRepository;


    @ShellMethod("Добавить студента")
    public String addStudent(String firstName, String lastName, int age) {
        Student newStudent = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
        studentRepository.save(newStudent);
        return "Студент добавлен: " + firstName + " " + lastName;
    }

    @ShellMethod("Показать всех студентов")
    public String showAllStudents() {
        // Реализация показа всех студентов
        return "Список студентов: ...";
    }

    @ShellMethod("Удалить студента")
    public String deleteStudent(Long id) {
        // Реализация удаления студента
        return "Студент удален с ID: " + id;
    }

    // Другие методы, например для поиска или изменения информации о студенте
}

