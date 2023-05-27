package com.example.customer.security;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceConfig implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomerDetails(user);
    }



//    @Override
//    public UserDetails updatePassword(@NotNull UserDetails user, String newPassword) {
//        userRepository.updatePasswordByUsername(user.getUsername(), newPassword);
//        return loadUserByUsername(user.getUsername());
//    }
}
