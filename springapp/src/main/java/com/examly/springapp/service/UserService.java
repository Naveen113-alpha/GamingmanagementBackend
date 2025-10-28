package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(hashedPassword);
        }
        
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(java.time.LocalDate.now());
        }
        
        user.setActive(true);
        
        return userRepository.save(user);
    }

    // Update user (re-hash if password changed)
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());

            if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPasswordHash());
                user.setPasswordHash(hashedPassword);
            }

            user.setRole(updatedUser.getRole());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            user.setCountry(updatedUser.getCountry());
            user.setParentalConsent(updatedUser.isParentalConsent());
            user.setCreatedDate(updatedUser.getCreatedDate());
            user.setLastLogin(updatedUser.getLastLogin());
            user.setActive(updatedUser.isActive());

            return userRepository.save(user);
        });
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
