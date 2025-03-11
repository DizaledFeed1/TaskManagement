package com.example.taskmanagement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminPanel")
public class AdminPanelController {
    @GetMapping
    public String getAdminPanel() {
        return "Welcome to the Admin Panel!";
    }
}

