package com.example.JPARestAPI.service;

import com.example.JPARestAPI.DTO.PasswordDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String generateAndSetNewPassword(Integer id);
    boolean updatePassword(Integer id, PasswordDTO passwordDTO);
}
