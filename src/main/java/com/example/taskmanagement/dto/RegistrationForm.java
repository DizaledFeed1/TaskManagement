package com.example.taskmanagement.dto;

import com.example.taskmanagement.data.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String role;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password),Set.of(role));
    }
//    public boolean isPasswordMatching() {
//        return password.equals(confirm);
//    }
}
