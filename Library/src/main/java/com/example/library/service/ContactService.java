package com.example.library.service;

import com.example.library.model.Contact;
import com.example.library.repository.ContactRepository;
import com.example.library.service.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    ContactRepository repo;

    public List<Contact> listAll(){
        return  (List<Contact>) repo.findAll();
    }

    public void save(Contact contact) {
        repo.save(contact);

    }

    public Contact get(Long id) throws UserNotFoundException {
        Optional<Contact> result =  repo.findById(id);

        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find users with ID" + id);
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new UserNotFoundException("Could not find users with ID" + id);
        }
        repo.deleteById(id);
    }
}
