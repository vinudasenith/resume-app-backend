package com.example.resumeapp.resume_app.controller;

import com.example.resumeapp.resume_app.model.User;
import com.example.resumeapp.resume_app.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.example.resumeapp.resume_app.security.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

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

    // user login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        Map<String, String> response = new HashMap<>();

        if (loggedInUser != null) {
            String token = jwtUtil.generateToken(loggedInUser.getEmail(), loggedInUser.getRole());
            response.put("token", token);
            response.put("role", loggedInUser.getRole());
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

    // pass user own date
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("Authorization") String authHeader) {
        try {

            String token = authHeader.replace("Bearer ", "");
            String email = jwtUtil.extractEmail(token);
            User user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("email", user.getEmail());
            userData.put("firstName", user.getFirstName());
            userData.put("lastName", user.getLastName());
            userData.put("username", user.getUsername());
            userData.put("role", user.getRole());

            return ResponseEntity.ok(userData);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

    }

    // check user role(admin)
    @GetMapping("/is-admin/{email}")
    public boolean isadmin(@PathVariable String email) {
        return userService.isAdmin(email);
    }

    // check user role(customer)
    @GetMapping("/is-customer/{email}")
    public boolean isCustomer(@PathVariable String email) {
        return userService.isCustomer(email);
    }

    // get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    // block user by email
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
