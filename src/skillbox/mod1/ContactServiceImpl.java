package skillbox.mod1;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactServiceImpl implements ContactService {

    private Map<String, Contact> contacts = new HashMap<>();

    @Override
    public void addContact(Contact contact) {
        contacts.put(contact.getEmail(), contact);
    }

    @Override
    public void deleteContact(String email) {
        contacts.remove(email);
    }

    @Override
    public void displayAllContacts() {
        for (Contact contact : contacts.values()) {
            System.out.println(contact.toString());
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }
}
