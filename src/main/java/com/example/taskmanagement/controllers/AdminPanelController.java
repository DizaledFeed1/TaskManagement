package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.dto.UserDto;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.data.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/adminPanel")
@Tag(name = "Админ панель", description = "API для управления панелью администратора")
public class AdminPanelController {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getAdminPanel(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {

        Pageable pageable = PageRequest.of(page, size);
        List<User> executors = userRepo.findExecutorUsers("ROLE_EXECUTOR");
        List<User> admins = userRepo.findExecutorUsers("ROLE_ADMIN");
        List<UserDto> executorsDto = userService.addUser(executors);
        List<UserDto> adminsDto = userService.addUser(admins);
        Page<Task> tasks = taskRepo.findAll(pageable);

        model.addAttribute("tasks", tasks);
        model.addAttribute("executors", executorsDto);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("admins", adminsDto);
        return "adminPanel";
    }

    @PostMapping("/filterExecutor")
    public String filterExecutor(@RequestParam("executorId") Long executorId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepo.findTaskByExecutor_Id(executorId, pageable);

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filtered", true);
        return "adminPanel";
    }

    @PostMapping("/filterAdmin")
    public String filterAdmin(@RequestParam("adminId") Long adminId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepo.findTaskByAuthor_Id(adminId, pageable);

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filtered", true);
        return "adminPanel";
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
        model.addAttribute("task", task);
        return "editTask";
    }

    @Operation(summary = "Сохранение изменений в задаче", description = "Обновляет информацию о задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в переданных данных")
    })
    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task,
                           @RequestParam("taskId") Long taskId) {
        task.setId(taskId);
        taskService.uppdate(task, taskId);
        return "redirect:/adminPanel";
    }
}
