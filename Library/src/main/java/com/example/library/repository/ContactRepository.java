package com.example.library.repository;

import com.example.library.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Long countById(Long id);
}
