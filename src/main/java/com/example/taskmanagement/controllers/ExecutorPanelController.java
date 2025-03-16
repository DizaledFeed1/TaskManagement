package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.taskmanagement.data.entity.User;

@Controller
@RequestMapping("/executorPanel")
@Tag(name = "Executor Panel", description = "API для панели исполнителя")
public class ExecutorPanelController {
    @Autowired
    private TaskRepo taskRepo;

    @GetMapping
    @Operation(summary = "Получить список задач для исполнителя",
            description = "Возвращает список задач для текущего исполнителя с пагинацией.")
    public String getExecutorPanel(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model, Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size);
        User currentUser = (User) authentication.getPrincipal();

        Page<Task> tasks = taskRepo.findTaskByExecutor_Id(currentUser.getId(), pageable);

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("currentPage", page);
        return "executorPanel";
    }

}
