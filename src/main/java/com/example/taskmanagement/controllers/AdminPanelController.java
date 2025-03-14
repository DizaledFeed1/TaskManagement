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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
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

    @Operation(summary = "Получение админ-панели", description = "Возвращает список задач, администраторов и исполнителей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно получен список"),
            @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса")
    })
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


    @Operation(summary = "Редактирование задачи", description = "Возвращает страницу редактирования задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
        model.addAttribute("task", task);
        return "editTask";
    }

    @Operation(summary = "Добавление комментария к задаче", description = "Добавляет комментарий к задаче по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации комментария")
    })
    @PostMapping("/addComment")
    public String addComment(@RequestParam("commentTaskId") Long commentTaskId,
                             @RequestParam("commentText") String comment,
                             RedirectAttributes redirectAttributes) {
        if (comment.length() < 5) {
            redirectAttributes.addFlashAttribute("error", "Введите комментарий");
            return "redirect:/adminPanel";
        }
        taskService.addComment(commentTaskId, comment);
        return "redirect:/adminPanel";
    }

    @Operation(summary = "Удаление задачи", description = "Удаляет задачу по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача с таким ID не найдена")
    })
    @PostMapping("/delete")
    public String deleteAdminPanel(@RequestParam("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        if (!taskRepo.existsById(taskId)) {
            redirectAttributes.addAttribute("error", "Задача с таким ID не существует!");
            return "redirect:/adminPanel";
        }
        taskRepo.deleteById(taskId);
        return "redirect:/adminPanel";
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

    @Operation(summary = "Фильтрация задач по исполнителю", description = "Фильтрует задачи по ID исполнителя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Фильтрация успешно выполнена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса")
    })
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

    @Operation(summary = "Фильтрация задач по админу", description = "Фильтрует задачи по ID автора (админа)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Фильтрация успешно выполнена"),
            @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса")
    })
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
}
