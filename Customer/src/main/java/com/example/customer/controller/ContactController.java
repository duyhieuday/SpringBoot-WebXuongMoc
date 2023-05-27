package com.example.customer.controller;


import com.example.library.dto.ContactDto;
import com.example.library.model.Contact;
import com.example.library.model.User;
import com.example.library.repository.ContactRepository;
import com.example.library.repository.UserRepository;
import com.example.library.security.jwt.JwtCookiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class ContactController {

    private final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactRepository contactRepository;

    private final JwtCookiesManager jwtCookiesManager;

    public ContactController(JwtCookiesManager jwtCookiesManager) {
        this.jwtCookiesManager = jwtCookiesManager;
    }

    @GetMapping("/register_lienhe")
    public String registerLienhe(Model model) {
        model.addAttribute("contactDto", new ContactDto());
        return "LienHe";
    }

    @RequestMapping("/process_lienhe")
    public String processLienhe(Contact contact) {


        contactRepository.save(contact);

        return "Index";
    }
}
