package com.mohit.scag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mohit.scag.model.User;
import com.mohit.scag.model.UserPrincipal;
import com.mohit.scag.repository.UserRepository;

@Service
public class UserDataService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=  userRepository.findByUsername(username);
        if(user==null) throw new UsernameNotFoundException("User not found");
         return new UserPrincipal(user);
    } 
    
}
