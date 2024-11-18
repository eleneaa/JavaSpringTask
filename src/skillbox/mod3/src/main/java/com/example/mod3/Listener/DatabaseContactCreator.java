package com.example.mod3.Listener;


import com.example.mod3.Contact;
import com.example.mod3.Service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.desktop.AppReopenedEvent;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactCreator {

    private final ContactService contactService;

    @EventListener(AppReopenedEvent.class)
    public void createContactData(){
        log.debug("Calling DatabaseContactCreator->createContactData");

        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            int value = i+1;
            Contact contact = new Contact();
            contact.setId((long) value);
            contact.setFirstName("Firstname" + value);
            contact.setLastName("Lastname"+ value);
            contact.setEmail("Email" + value);
            contact.setPhoneNumber("PhoneNumber"+ value);

            contacts.add(contact);
        }

        contactService.batchInsert(contacts);

    }
}
