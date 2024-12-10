package com.mohit.scag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.scag.model.User;
import com.mohit.scag.repository.UserRepository;
@Service
public class UserService {
     @Autowired
    private UserRepository userRepository;
    public User save(User user) {
      return userRepository.save(user);

    }
    
}
