package com.example.JPARestAPI.service.imp;

import com.example.JPARestAPI.DTO.PasswordDTO;
import com.example.JPARestAPI.model.User;
import com.example.JPARestAPI.repository.UserRepository;
import com.example.JPARestAPI.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public boolean updatePassword(Integer id, PasswordDTO passwordDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String oldPassword = passwordDTO.getOldPassword();
            String newPassword = passwordDTO.getNewPassword();

            // Kiểm tra mật khẩu cũ có khớp với mật khẩu hiện tại không
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
    @Override
    public String generateAndSetNewPassword(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String newPassword = generateRandomPassword();

            user.setPassword(newPassword);
            userRepository.save(user);

            return newPassword;
        }
        return null;
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8; // Độ dài của mật khẩu mới

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
