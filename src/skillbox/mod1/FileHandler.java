package skillbox.mod1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public List<Contact> readContactsFromFile(String filePath) {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactData = line.split(";");
                if (contactData.length == 3) {
                    contacts.add(new Contact(contactData[0], contactData[1], contactData[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return contacts;
    }

    public void writeContactsToFile(String filePath, List<Contact> contacts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                writer.write(String.join(";", contact.getFullName(), contact.getPhoneNumber(), contact.getEmail()) + "\n");
            }
            System.out.println("Контакты успешно записаны в файл: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл создан: " + filePath);
                } else {
                    System.out.println("Не удалось создать файл");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла: " + e.getMessage());
            }
        }
    }
}

