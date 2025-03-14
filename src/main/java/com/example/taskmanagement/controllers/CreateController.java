package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.data.repository.UserRepo;
import com.example.taskmanagement.dto.CreateDto;
import com.example.taskmanagement.dto.UserDto;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Create", description = "API для создания задачи")
public class CreateController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;


    @Operation(summary = "Показать форму для создания задачи", description = "Отображает страницу для создания новой задачи с выбором исполнителей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с формой для создания задачи отображена успешно."),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
    })
    @GetMapping("/create")
    public String create(Model model) {
        List<User> executors = userRepo.findExecutorUsers("ROLE_EXECUTOR");
        List<UserDto> executorsDto = userService.addUser(executors);
        model.addAttribute("executors", executorsDto);
        return "create";
    }

    @ModelAttribute("createDto")
    public CreateDto createDto() {
        return new CreateDto();
    }


    @Operation(summary = "Создать новую задачу", description = "Обрабатывает данные формы для создания новой задачи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана."),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания задачи."),
            @ApiResponse(responseCode = "500", description = "Ошибка при создании задачи.")
    })
    @PostMapping("/create")
    public String create(@ModelAttribute CreateDto task) {
        taskService.createTask(task);
        return "create";
    }
}
