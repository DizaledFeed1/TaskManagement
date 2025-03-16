package com.example.taskmanagement.controllers.api;

import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.enums.Status;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = {"EXECUTOR"})
public class ExecutorPanelApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Mock
//    private TaskService taskService;

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskService taskService;

    @InjectMocks
    private ExecutorPaneApilController executorPaneApilController;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(9L);
        task.setStatus(Status.COMPLETED);
    }


    @Test
    public void testUpdateStatus() {
        // Создаём тестовую задачу
        Task task = new Task();
        task.setStatus(Status.COMPLETED);

        Task existingTask = new Task();
        existingTask.setId(9L);
        existingTask.setStatus(Status.IN_PROGRESS);

        // Настройка мока: когда вызывается findTaskById с id = 7, возвращаем существующую задачу
        when(taskRepo.findTaskById(9L)).thenReturn(existingTask);

        // Метод, который будет вызван
        taskService.uppdateStatus(existingTask, 9L);

        // Проверяем, что статус задачи был обновлён
        assertEquals(Status.COMPLETED, existingTask.getStatus());

        // Проверяем, что метод save был вызван с обновлённой задачей
        verify(taskRepo).save(existingTask);
    }
}
