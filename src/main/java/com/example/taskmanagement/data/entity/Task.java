package com.example.taskmanagement.data.entity;

import com.example.taskmanagement.data.enums.Priority;
import com.example.taskmanagement.data.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Schema(description = "Сущность задачи")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id задачи", example = "1")
    private Long id;

    @NotBlank(message = "Укажите заголовок!")
    @Schema(description = "Заголовок задачи", example = "Первая задача")
    private String header;

    @NotBlank(message = "Укажите описание!")
    @Schema(description = "Описание задачи", example = "Пройти отбор")
    private String description;

    @NotBlank(message = "Укажите статус!")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Статуст задачи", example = "В процессе")
    private Status status;

    @NotBlank(message = "Укажите приоритет!")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Приоритет задачи", example = "Высокий")
    private Priority priority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @Schema(description = "Комментарий к задаче", example = "Комментарий")
    private List<Comment> comment = new ArrayList<>(); //Может быть пустым

    @ManyToOne
    @JoinColumn(name = "author_id")
    @Schema(description = "Автор задачи", example = "Author@gmail.com")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    @Schema(description = "Исполнитель задачи", example = "Executor@gmail.com")
    private User executor;
}
