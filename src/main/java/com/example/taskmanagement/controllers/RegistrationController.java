package com.example.taskmanagement.controllers;

import com.example.taskmanagement.data.repository.UserRepo;
import com.example.taskmanagement.dto.RegistrationForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/register")
@Tag(name = "Registration", description = "API для регистрации пользователей")
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Получить форму для регистрации",
            description = "Возвращает форму для регистрации нового пользователя.")
    public String registerForm() {
        return "register";
    }
    @PostMapping
    @Operation(summary = "Зарегистрировать нового пользователя",
            description = "Обрабатывает регистрацию нового пользователя, сохраняет данные в базу данных.")
    public String processRegistration(RegistrationForm form) {
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
