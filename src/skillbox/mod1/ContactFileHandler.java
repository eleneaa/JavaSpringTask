package skillbox.mod1;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactFileHandler {

    public List<Contact> readFromFile(String fileName) {
        List<Contact> contacts = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/skillbox/mod1/resources/" + fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactInfo = line.split(";");
                if (contactInfo.length == 3) {
                    Contact contact = new Contact();
                    contact.setFullName(contactInfo[0]);
                    contact.setPhoneNumber(contactInfo[1]);
                    contact.setEmail(contactInfo[2]);
                    contacts.add(contact);
                } else {
                    System.err.println("Ошибка чтения контакта из файла: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении из файла: " + e.getMessage());
        }
        return contacts;
    }

    public void writeToFile(String fileName, List<Contact> contacts) {
        try (OutputStream outputStream = new FileOutputStream(new File("/skillbox/mod1/resources/" + fileName));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            for (Contact contact : contacts) {
                String line = contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

}


