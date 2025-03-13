package com.example.taskmanagement.services;

import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<UserDto> addUser(List<User> executors) {
        List<UserDto> users = new ArrayList<>();
        for (User user : executors) {
            users.add(new UserDto(user.getId(), user.getUsername()));
        }

        return users;
    }
}
