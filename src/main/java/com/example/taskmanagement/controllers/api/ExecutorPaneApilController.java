package com.example.taskmanagement.controllers.api;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/executorPanel")
public class ExecutorPaneApilController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/addComment")
    @Operation(
            summary = "Добавить комментарий к задаче",
            description = "Позволяет исполнителю добавить комментарий к задаче.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка: комментарий слишком короткий")
            }
    )
    public ResponseEntity<Object> addComment(@RequestParam("commentTaskId") Long commentTaskId,
                                             @RequestParam("commentText") String  comment, RedirectAttributes redirectAttributes) {
        if (comment.length() < 5) {
            return ResponseEntity.badRequest().body("{\"error\":\" Минимальный размер комментария 5 символов");
        }
        taskService.addComment(commentTaskId, comment);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/executorPanel")
                .build();
    }

    @PostMapping("/updateStatus")
    @Operation(
            summary = "Обновить статус задачи",
            description = "Позволяет исполнителю обновить статус задачи.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус задачи успешно обновлён"),
                    @ApiResponse(responseCode = "400", description = "Ошибка: недействительный статус или ID задачи")
            }
    )
    public ResponseEntity<Object> updateStatus(@RequestParam("taskId") Long taskId,
                               @ModelAttribute Task task){
        taskService.uppdateStatus(task, taskId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/executorPanel")
                .build();
    }
}
