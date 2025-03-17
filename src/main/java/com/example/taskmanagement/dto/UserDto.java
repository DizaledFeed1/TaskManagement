package com.example.taskmanagement.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
public class UserDto {
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String name;

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

