package skillbox.mod1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run(ContactServiceImpl contactManager) {
        return (args) -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Выберите вариант действия:");
                System.out.println("1. Вывести все контакты\n2. Добавить контакт\n3. Удалить контакт\n4. Сохранить контакты и выйти");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Чтение символа новой строки после ввода числа

                switch (choice) {
                    case 1 -> contactManager.displayAllContacts();
                    case 2 -> {
                        System.out.println("Введите данные контакта в формате Ф. И. О.;номер телефона;адрес электронной почты:");
                        String input = scanner.nextLine();
                        String[] dataContact = input.split(";");
                        Contact newContact = new Contact(dataContact[0], dataContact[1], dataContact[2]);
                        contactManager.addContact(newContact);
                    }
                    case 3 -> {
                        System.out.println("Введите адрес электронной почты контакта для удаления:");
                        String email = scanner.nextLine();
                        contactManager.deleteContact(email);
                    }
                    case 4 -> {
                        contactManager.saveContactsToFile();
                        System.out.println("Контакты сохранены в файл. Завершение работы.");
                        return;
                    }
                    default -> System.out.println("Некорректный ввод. Попробуйте снова.");
                }
            }
        };
    }
}


