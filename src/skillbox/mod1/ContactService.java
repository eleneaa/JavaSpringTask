package skillbox.mod1;

import java.util.List;

public interface ContactService {
    void addContact(Contact contact);
    void deleteContact(String email);
    void displayAllContacts();
    List<Contact> getAllContacts();
}

