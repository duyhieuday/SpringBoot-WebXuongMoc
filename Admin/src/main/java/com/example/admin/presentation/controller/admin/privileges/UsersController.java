package com.example.admin.presentation.controller.admin.privileges;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.model.Enums;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.AdminService;
import com.example.library.service.CustomerService;
import com.example.library.service.impl.UserNotFoundException;
import com.example.library.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/privileges/users")
public class UsersController extends BaseController {

    @Autowired
    private AdminService service;

    @Autowired
    private CustomerService customerService;

    final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @RequestMapping
    public ModelAndView index() {
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Users")
                .setContent(Fragment.of("templates/admin/pages/privileges/users.html")))
                .addObject("users", repository.findAll());
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Users/Add")
                .setContent(Fragment.of("templates/admin/pages/privileges/users.html").setName("add_user")));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return index();
    }

    @PostMapping("/save")
    public ModelAndView saveUser(User user, RedirectAttributes ra) {
        service.save(user);

        ra.addFlashAttribute("message", "The user has been saved successfully. ");
        return index();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        try {
            User user = service.get(id);

            return view(buildContext(Resource.Layout.MasterAdmin)
                    .setTitle("Users/Edit")
                    .setContent(Fragment.of("templates/admin/pages/privileges/user_form.html").setName("edit_user")))
                    .addObject("user", user);

        } catch (UserNotFoundException e) {
            return index();
        }
    }

    @PostMapping("/edit")
    public ModelAndView editUser(User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setStatus(Enums.Status.ACTIVE);
        service.save(user);

        return index();
    }
//    @RequestMapping(value = "/edit", method = {RequestMethod.GET, RequestMethod.PUT})
//    public ModelAndView updateCustomer(
//            @ModelAttribute("user") User user,
//            Model model,
//            RedirectAttributes redirectAttributes,
//            Principal principal){
//        if(principal == null){
//            return index();
//        }
//        User customerSaved = customerService.saveInfor(user);
//
//        redirectAttributes.addFlashAttribute("user", customerSaved);
//
//        return index();
//    }


}
