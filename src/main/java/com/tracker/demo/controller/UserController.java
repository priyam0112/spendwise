package com.tracker.demo.controller;

import java.util.List;

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
    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User register(@Valid @RequestBody User user){
        System.out.println("Registering user: " + user.getEmail() + " | " + user.getPassword());
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getUser();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }

}
