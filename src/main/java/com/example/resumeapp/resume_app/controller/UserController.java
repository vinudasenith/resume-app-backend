package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.User;
import com.example.resumeapp.resume_app.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    // user registration
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        Map<String, String> response = new HashMap<>();

        if (savedUser != null) {
            response.put("message", "User registration successful");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    // set user login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

    // check user role
    @GetMapping("/is-admin/{email}")
    public boolean isadmin(@PathVariable String email) {
        return userService.isAdmin(email);
    }

    @GetMapping("/is-customer/{email}")
    public boolean isCustomer(@PathVariable String email) {
        return userService.isCustomer(email);
    }

    // get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @PutMapping("/block/{email}")
    public ResponseEntity<String> toggleUserStatus(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setEnabled(!user.isEnabled());
        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }

}
