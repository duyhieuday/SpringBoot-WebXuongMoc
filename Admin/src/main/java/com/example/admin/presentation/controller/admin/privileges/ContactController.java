package com.example.admin.presentation.controller.admin.privileges;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.model.Contact;
import com.example.library.model.Enums;
import com.example.library.model.User;
import com.example.library.repository.ContactRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.AdminService;
import com.example.library.service.ContactService;
import com.example.library.service.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/privileges/contacts")
public class ContactController extends BaseController{

    @Autowired
    private ContactService service;

    final ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    @RequestMapping
    public ModelAndView index() {

        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Contacts")
                .setContent(Fragment.of("templates/admin/pages/privileges/contacts.html")))
                .addObject("contacts", repository.findAll());
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteContact(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return index();
    }

    @PostMapping("/save")
    public ModelAndView saveContact(Contact contact, RedirectAttributes ra) {
        service.save(contact);

        ra.addFlashAttribute("message", "The user has been saved successfully. ");
        return index();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        try {
            Contact contact = service.get(id);

            return view(buildContext(Resource.Layout.MasterAdmin)
                    .setTitle("Contact/Edit")
                    .setContent(Fragment.of("templates/admin/pages/privileges/contact_form.html").setName("edit_contact")))
                    .addObject("contact", contact);

        } catch (UserNotFoundException e) {
            return index();
        }
    }

    @PostMapping("/edit")
    public ModelAndView editContact(Contact contact) {

        service.save(contact);

        return index();
    }



}
