package skillbox.mod1;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    String filename = "default-contacts.txt";
    private final ContactService contactService;
    private final Scanner scanner;
    private final ContactFileHandler contactFileHandler;

    public ConsoleUI(ContactService contactService, ContactFileHandler contactFileHandler) {
        this.contactService = contactService;
        this.contactFileHandler = contactFileHandler;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the console contact management!");

        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a contact");
            System.out.println("2. Delete a contact");
            System.out.println("3. Print all contacts");
            System.out.println("4. Save contact to file");
            System.out.println("5. Load contact from file");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume new line

            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter phone number:");
                    String phoneNumber = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber, email);
                    contactService.addContact(newContact);
                    System.out.println("Contact added");
                    break;
                case 2:
                    System.out.println("Enter the email of the contact to delete:");
                    String emailToDelete = scanner.nextLine();
                    contactService.deleteContact(emailToDelete);
                    System.out.println("Contact with email " + emailToDelete + " deleted");
                    break;
                case 3:
                    contactService.displayAllContacts();
                    break;
                case 4:
                    List<Contact> contacts = contactService.getAllContacts();
                    contactFileHandler.writeToFile(filename, contacts);
                    System.out.println("Contacts saved to file");
                    break;
                case 5:
                    List<Contact> contactsFromFile = contactFileHandler.readFromFile(filename);
                    for (Contact contact : contactsFromFile) {
                        contactService.addContact(contact);
                    }
                    System.out.println("Contacts loaded from file");
                    break;
                case 6:
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

