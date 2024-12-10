package com.mohit.scag.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mohit.scag.model.User;
import com.mohit.scag.service.ActivityLogService;
import com.mohit.scag.service.JwtService;
import com.mohit.scag.service.UserService;

@RestController

public class UserController {
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }
    @PostMapping("login")
    public String login(@RequestBody User user) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
        String token = jwtService.generateToken(user);
        System.out.println(token);
        return token;    
        }
        else return "failed";
    }
    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping("/dashboard")
    public ResponseEntity<String> getUserDashboard(Principal principal) {
        activityLogService.logActivity(principal.getName(), "Accessed Dashboard");
        return ResponseEntity.ok("Welcome to the user dashboard");
    }
    
}
