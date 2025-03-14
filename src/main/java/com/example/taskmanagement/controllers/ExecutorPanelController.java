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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.taskmanagement.data.entity.User;

@RestController
@RequestMapping("/executorPanel")
@Tag(name = "Executor Panel", description = "API для панели исполнителя")
public class ExecutorPanelController {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskService taskService;

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

    @PostMapping("/addComment")
    @Operation(summary = "Добавить комментарий к задаче",
            description = "Позволяет исполнителю добавить комментарий к задаче.")
    public String addComment(@RequestParam("commentTaskId") Long commentTaskId,
                             @RequestParam("commentText") String  comment, RedirectAttributes redirectAttributes) {
        if (comment.length() < 1) {
            redirectAttributes.addFlashAttribute("error", "Введите комментарий");
            return "redirect:/executorPanel";
        }
        taskService.addComment(commentTaskId, comment);
        return "redirect:/executorPanel";
    }
    @PostMapping("/updateStatus")
    @Operation(summary = "Обновить статус задачи",
            description = "Позволяет исполнителю обновить статус задачи.")
    public String updateStatus(@RequestParam("taskId") Long taskId,
                               @ModelAttribute Task task){
        taskService.uppdateStatus(task, taskId);
        return "redirect:/executorPanel";
    }
}
