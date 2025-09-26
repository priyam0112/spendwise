package com.tracker.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.demo.model.User;
import com.tracker.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepo.save(u);
    }

    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public List<User> getUser(){
        return userRepo.findAll();
    }
}
