package skillbox.mod1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    @Value("${contacts.load.path}")
    private String loadFilePath;

    @Value("${contacts.save.path}")
    private String saveFilePath;
    private List<Contact> contacts;

    public ContactServiceImpl() {
        this.contacts = new ArrayList<>();
    }

    @Override
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Контакт успешно добавлен: " + contact.getFullName());
    }


    @Override
    public void deleteContact(String email) {
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getEmail().equals(email)) {
                iterator.remove(); // Удаляем контакт, если email соответствует
            }
        }
    }

    @Override
    public void displayAllContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    @Override
    public void saveContactsToFile() {
        try (FileWriter fileWriter = new FileWriter("contacts.txt")) {
            for (Contact contact : contacts) {
                fileWriter.write(contact.toString() + "\n");
            }
            System.out.println("Контакты сохранены в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении контактов в файл: " + e.getMessage());
        }
    }


}