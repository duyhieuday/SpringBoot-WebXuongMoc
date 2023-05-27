package com.example.customer.controller;

import com.example.library.dto.ContactDto;
import com.example.library.dto.CustomerDto;
import com.example.library.model.Enums;
import com.example.library.model.LoginError;
import com.example.library.model.Role;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.security.jwt.JwtCookiesManager;
import com.example.library.service.CustomerService;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    private final JwtCookiesManager jwtCookiesManager;

    public AuthController(JwtCookiesManager jwtCookiesManager) {
        this.jwtCookiesManager = jwtCookiesManager;
    }

    @GetMapping("/login")
    public ModelAndView login(@NotNull HttpServletRequest request, @RequestParam(value = "error", required = false) String error) {
        if (jwtCookiesManager.getLoginSessionToken(request) != null) {
            return redirect();
        }
        return new ModelAndView("login").addObject("error", LoginError.decode(error));
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "dky";
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        // not handle
        return null;
    }

    @GetMapping("/redirect")
    public ModelAndView redirect() {
        LOGGER.warn("Redirect {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return new ModelAndView("redirect:/shop/home");
    }

//    @PostMapping("/do-register")
//    public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
//                                  BindingResult result,
//                                  Model model) {
//        try {
//            if (result.hasErrors()) {
//                model.addAttribute("customerDto", customerDto);
//                return "dky";
//            }
//            User customer = userRepository.findByUsername(customerDto.getUsername());
//            if(customer != null){
//                model.addAttribute("username", "Username have been registered");
//                model.addAttribute("customerDto",customerDto);
//                return "dky";
//            }
//            if(customerDto.getPassword() != null){
//                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
//                customerService.save(customerDto);
//                model.addAttribute("success", "Register successfully");
//                return "dky";
//            }
//        }catch (Exception e){
//            model.addAttribute("error", "Server have ran some problems");
//            model.addAttribute("customerDto",customerDto);
//        }
//        return "dky";
//    }

    @RequestMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = new Role();
        role.setId(3L);
        user.setRole(role);
        user.setStatus(Enums.Status.ACTIVE);

        userRepository.save(user);

        return "login";
    }


}
