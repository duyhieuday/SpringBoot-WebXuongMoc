package com.example.library.service;

import com.example.library.dto.CustomerDto;
import com.example.library.model.User;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    User findByUsername(String username);

    User saveInfor(User customer);
}
