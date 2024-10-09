package skillbox.mod1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProfileInitializer {

    private Properties properties;

    public ProfileInitializer() {
        // Загрузка конфигурационных свойств из файла
        properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке конфигурационного файла: " + e.getMessage());
        }
    }

    public void initializeProfile() {
        // Проверка типа профиля приложения и выполнение соответствующих действий
        String profileType = properties.getProperty("profile.type");
        if ("init".equals(profileType)) {
            System.out.println("Инициализация профиля типа 'init'");
            // Получение пути к файлу с контактами из конфигурации и загрузка контактов
            String contactsFilePath = properties.getProperty("contacts.file.path");
            loadContactsFromFile(contactsFilePath);
        } else {
            System.out.println("Другой тип профиля: " + profileType);
        }
    }

    private void loadContactsFromFile(String filePath) {
        // Чтение контактов из файла и добавление их в список контактов
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Разделение строки на отдельные данные контакта и добавление в список
                String[] contactData = line.split(";");
                if (contactData.length == 3) {
                    contacts.add(new Contact(contactData[0], contactData[1], contactData[2]));
                } else {
                    System.err.println("Ошибка: Некорректный формат данных контакта в файле");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла контактов: " + e.getMessage());
        }

        // Добавить contacts в ваше хранилище контактов
    }
}


