package com.example.taskmanagement.dto;

import com.example.taskmanagement.data.enums.Priority;
import com.example.taskmanagement.data.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateDto {
    @Schema(description = "Заголовок задачи", example = "Исправить баг в отчётах")
    private String header;

    @Schema(description = "Описание задачи", example = "При генерации отчёта за месяц выводятся неверные суммы")
    private String description;

    @Schema(description = "Статус задачи", example = "WAITTING")
    private Status status;

    @Schema(description = "Приоритет задачи", example = "HIGH")
    private Priority priority;

    @Schema(description = "Комментарий к задаче", example = "Этот баг появился после обновления сервера")
    private String comment;

    @Schema(description = "ID исполнителя", example = "2")
    private Long executor;
}
