package skillbox.mod1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Создание экземпляра ContactService (предполагается, что этот код уже у вас есть)
        ContactService contactService = new ContactServiceImpl();
        ContactFileHandler contactFileHandler = new ContactFileHandler();// Пример имплементации ContactService

        // Создание экземпляра ConsoleUI и запуск консольного управления
        ConsoleUI consoleUI = new ConsoleUI(contactService, contactFileHandler);
        consoleUI.start();
    }
}


