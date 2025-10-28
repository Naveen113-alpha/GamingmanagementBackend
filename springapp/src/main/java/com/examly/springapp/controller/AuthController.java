package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        String role = loginRequest.get("role");

        System.out.println("Login attempt - Email: " + email + ", Role: " + role);
        

        
        // First try to find user by email and role
        Optional<User> userOpt = userRepository.findByEmailAndRole(email, role);
        
        // If not found, try to find by email only
        if (!userOpt.isPresent()) {
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail != null) {
                System.out.println("User found by email only - Role: " + userByEmail.getRole());
                // If user exists but with different role, update role to PLAYER
                if ("PLAYER".equals(role)) {
                    userByEmail.setRole("PLAYER");
                    userRepository.save(userByEmail);
                    userOpt = Optional.of(userByEmail);
                }
            }
        }
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("User found - ID: " + user.getId() + ", Username: " + user.getUsername());
            System.out.println("User role: " + user.getRole());
            System.out.println("User active: " + user.isActive());
            
            if (!user.isActive()) {
                user.setActive(true);
                userRepository.save(user);
                System.out.println("User activated");
            }
            
            System.out.println("Stored hash: " + user.getPasswordHash());
            System.out.println("Input password: " + password);
            System.out.println("Password matches: " + passwordEncoder.matches(password, user.getPasswordHash()));
            
            // Check if password matches OR if it's a plain text match (for debugging)
            if (passwordEncoder.matches(password, user.getPasswordHash()) || password.equals(user.getPasswordHash())) {
                // If plain text match, re-encode the password
                if (password.equals(user.getPasswordHash())) {
                    user.setPasswordHash(passwordEncoder.encode(password));
                    userRepository.save(user);
                    System.out.println("Password re-encoded");
                }
                
                user.setLastLogin(LocalDate.now());
                userRepository.save(user);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            } else {
                System.out.println("Password mismatch - Hash: " + user.getPasswordHash().substring(0, 20) + "...");
            }
        } else {
            System.out.println("No user found with email: " + email);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Invalid credentials");
        return ResponseEntity.badRequest().body(response);
    }
    

    

}