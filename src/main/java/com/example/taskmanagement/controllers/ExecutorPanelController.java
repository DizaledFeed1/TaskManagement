package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/executorPanel")
public class ExecutorPanelController {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getExecutorPanel(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
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
