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
@RequestMapping("api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }

    @GetMapping("/is-admin/{email}")
    public boolean isadmin(@PathVariable String email) {
        return userService.isAdmin(email);
    }

    @GetMapping("/is-customer/{email}")
    public boolean isCustomer(@PathVariable String email) {
        return userService.isCustomer(email);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }
}
