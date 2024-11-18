package com.example.mod3.Service;

import com.example.mod3.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void batchInsert (List<Contact> contacts);
}
