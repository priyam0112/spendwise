package com.tracker.demo.controller;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.demo.model.User;
import com.tracker.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public User register(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getUser();
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody User loginRequest){
        User user = userService.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Login Successful";
        } else {
            return "Invalid Credentials";
        }
    }
}
