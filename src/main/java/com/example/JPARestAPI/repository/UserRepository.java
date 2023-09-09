package com.example.JPARestAPI.repository;

import com.example.JPARestAPI.DTO.AvatarDTO;
import com.example.JPARestAPI.model.User;
import com.example.JPARestAPI.DTO.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContainingIgnoreCase(String name);
    User getUserById(Integer id);
    User createUser(User user);
    User updateUser(Integer id, UserDTO user);
    boolean deleteUser(Integer id);
    User updateAvatar(Integer id, AvatarDTO avatarDTO);

}
