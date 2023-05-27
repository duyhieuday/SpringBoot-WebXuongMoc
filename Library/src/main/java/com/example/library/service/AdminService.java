package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    UserRepository repo;

    public List<User> listAll(){
        return  (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);

    }

    public User get(Long id) throws UserNotFoundException {
        Optional<User> result =  repo.findById(id);

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
