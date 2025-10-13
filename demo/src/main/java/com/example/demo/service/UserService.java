package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id :"+id));

    }

    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
        {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User updatedUser=getUserById(id);
        updatedUser
                .setFullName(user.getFullName());
        updatedUser
                .setRole(user.getRole());
        updatedUser
                .setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);


    }

    public void deleteUser(Long id) {
        User user=getUserById(id);
        userRepository.delete(user);
    }
}
