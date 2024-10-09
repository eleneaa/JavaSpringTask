package skillbox.mod1;

public interface ContactService {
    void addContact(Contact contact);
    void deleteContact(String email);
    void displayAllContacts();
    void saveContactsToFile();

}
