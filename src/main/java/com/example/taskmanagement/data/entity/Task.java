package com.example.taskmanagement.data.entity;

import com.example.taskmanagement.data.enums.Priority;
import com.example.taskmanagement.data.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Укажите заголовок!")
    private String header;

    @NotBlank(message = "Укажите описание!")
    private String description;

    @NotBlank(message = "Укажите статус!")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank(message = "Укажите приоритет!")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String comment; //Может быть пустым

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;
}
