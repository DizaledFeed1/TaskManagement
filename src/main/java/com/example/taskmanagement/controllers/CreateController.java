package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.data.repository.UserRepo;
import com.example.taskmanagement.dto.CreateDto;
import com.example.taskmanagement.dto.UserDto;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class CreateController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

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

    @PostMapping("/create")
    public String create(@ModelAttribute CreateDto task) {
        taskService.createTask(task);
        return "create";
    }
}
