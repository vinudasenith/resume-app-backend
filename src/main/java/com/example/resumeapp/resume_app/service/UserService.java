package com.example.resumeapp.resume_app.service;

import com.example.resumeapp.resume_app.model.User;
import com.example.resumeapp.resume_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // user registration method
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    // user login method
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }

    // check user role method
    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user.getRole().equals("admin");

    }

    // check user role method
    public boolean isCustomer(String email) {
        User user = userRepository.findByEmail(email);
        return user.getRole().equals("customer");
    }

    // get all users
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // get user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // save user
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
