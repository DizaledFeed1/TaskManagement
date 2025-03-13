package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.entity.Comment;
import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adminPanel")
public class AdminPanelController {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAdminPanel(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        return "adminPanel";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("commentTaskId") Long commentTaskId,
                             @RequestParam("commentText") String  comment, RedirectAttributes redirectAttributes) {
        if (comment.length() < 5) {
            redirectAttributes.addFlashAttribute("error", "Введите комментарий");
            return "redirect:/adminPanel";
        }
        taskService.addComment(commentTaskId, comment);
        return "redirect:/adminPanel";
    }

    @PostMapping("/delete")
    public String deleteAdminPanel(@RequestParam("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        if (!taskRepo.existsById(taskId)) {
            redirectAttributes.addAttribute("error", "Задача с таким ID не существует!");
            return "redirect:/adminPanel";
        }
        taskRepo.deleteById(taskId);
        return "redirect:/adminPanel";
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
        model.addAttribute("task", task);
        return "editTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task,
                           @RequestParam("taskId") Long taskId) {
        task.setId(taskId);
        taskService.uppdate(task, taskId);
        return "redirect:/adminPanel";
    }

}

