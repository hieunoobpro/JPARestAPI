package com.example.JPARestAPI.controller;

import com.example.JPARestAPI.DTO.AvatarDTO;
import com.example.JPARestAPI.DTO.PasswordDTO;
import com.example.JPARestAPI.model.User;
import com.example.JPARestAPI.DTO.UserDTO;
import com.example.JPARestAPI.repository.UserRepository;
import com.example.JPARestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/search")
    public List<User> searchUsersByName(@RequestParam("name") String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userRepository.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userRepository.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO user) {
        User updatedUser = userRepository.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        boolean deleted = userRepository.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/update-avatar")
    public ResponseEntity<User> updateAvatar(@PathVariable("id") Integer id, @RequestBody AvatarDTO avatarDTO) {
        User updatedUser = userRepository.updateAvatar(id, avatarDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/update-password")
    public ResponseEntity<String> updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordDTO passwordDTO) {
        boolean passwordUpdated = userService.updatePassword(id, passwordDTO);
        if (passwordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/forgot-password")
    public ResponseEntity<String> forgotPassword(@PathVariable("id") Integer id) {
        String newPassword = userService.generateAndSetNewPassword(id);
        if (newPassword != null) {
            return ResponseEntity.ok("New password: " + newPassword);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
