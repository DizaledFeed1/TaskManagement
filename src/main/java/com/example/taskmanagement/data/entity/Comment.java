package com.example.taskmanagement.data.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(description = "Модель комментария, связанного с задачей.")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор комментария", example = "1")
    private Long id;

    @Schema(description = "Текст комментария", example = "Это мой комментарий к задаче.")
    private String comment;

    @ManyToOne
    @Schema(description = "Задача, к которой относится комментарий")
    private Task task;
}
