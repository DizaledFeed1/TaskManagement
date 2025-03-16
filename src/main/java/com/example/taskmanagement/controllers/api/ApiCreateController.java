package com.example.taskmanagement.controllers.api;

import com.example.taskmanagement.dto.CreateDto;
import com.example.taskmanagement.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks/api/create")
public class ApiCreateController {
    @Autowired
    private TaskService taskService;

    @Operation(summary = "Создать новую задачу", description = "Обрабатывает данные формы для создания новой задачи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана."),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания задачи."),
            @ApiResponse(responseCode = "500", description = "Ошибка при создании задачи.")
    })
    @PostMapping()
    public ResponseEntity<Object> create(@ModelAttribute CreateDto task) {
        taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/adminPanel")
                .build();
    }
}
