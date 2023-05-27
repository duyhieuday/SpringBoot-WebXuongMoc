package com.example.library.service;


import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService, UserDetailsPasswordService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new UserDetail(user.get());
    }

    @Override
    public UserDetails updatePassword(@NotNull UserDetails user, String newPassword) {
        userRepository.updatePasswordByUsername(user.getUsername(), newPassword);
        return loadUserByUsername(user.getUsername());
    }
}

