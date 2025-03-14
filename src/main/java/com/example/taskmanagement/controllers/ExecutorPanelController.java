package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
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
public class ExecutorPanelController {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskService taskService;

    @GetMapping
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
    public String updateStatus(@RequestParam("taskId") Long taskId,
                               @ModelAttribute Task task){
        taskService.uppdateStatus(task, taskId);
        return "redirect:/executorPanel";
    }
}
