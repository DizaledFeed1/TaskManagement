package com.example.taskmanagement.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;

    // Правильный конструктор
    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

