package com.example.library.service.impl;

import com.example.library.dto.CustomerDto;
import com.example.library.model.User;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomerDto save(CustomerDto customerDto) {

        User user = new User();
        user.setUsername(customerDto.getUsername());
        user.setPassword(customerDto.getPassword());
        user.setEmail(customerDto.getEmail());
        user.setPhone(customerDto.getPhone());
        //user.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

        User customerSave = userRepository.save(user);
        return mapperDTO(customerSave);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveInfor(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        user1.setFirstname(user.getFirstname());
        user1.setLastname(user.getLastname());
        user1.setGender(user.getGender());
        user1.setAvatar(user.getAvatar());
        return userRepository.save(user1);
    }

    private CustomerDto mapperDTO(User user){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername(user.getUsername());
        customerDto.setPassword(user.getPassword());
        customerDto.setEmail(user.getEmail());
        customerDto.setPhone(user.getPhone());
        return customerDto;
    }
}
