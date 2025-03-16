package com.example.taskmanagement.controllers.api;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/adminPanel")
public class AdminPanelApiController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepo taskRepo;

    @Operation(summary = "Добавление комментария к задаче", description = "Добавляет комментарий к задаче по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации комментария")
    })
    @PostMapping("/addComment")
    public ResponseEntity<Object> addComment(@RequestParam("commentTaskId") Long commentTaskId,
                             @RequestParam("commentText") String comment) {
        if (comment.length() < 5) {
            return ResponseEntity.badRequest().body("{\"error\":\" Минимальный размер комментария 5 символов");
        }
        taskService.addComment(commentTaskId, comment);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/adminPanel")
                .build();
    }



    @Operation(summary = "Удаление задачи", description = "Удаляет задачу по ID (используется POST вместо DELETE)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача с таким ID не найдена")
    })
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteAdminPanel(@RequestParam("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.badRequest().body("{\"error\":\"Задача с таким ID не существует!\"}");
        }
        taskRepo.deleteById(taskId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/adminPanel")
                .build();
    }
}
