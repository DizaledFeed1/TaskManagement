package com.example.taskmanagement.services;

import com.example.taskmanagement.data.entity.Comment;
import com.example.taskmanagement.data.entity.Task;
import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.data.enums.Priority;
import com.example.taskmanagement.data.enums.Status;
import com.example.taskmanagement.data.repository.TaskRepo;
import com.example.taskmanagement.data.repository.UserRepo;
import com.example.taskmanagement.dto.CreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public void uppdateStatus(Task task, Long id) {
        Task newTask = taskRepo.findTaskById(id);
        newTask.setStatus(task.getStatus());
        taskRepo.save(newTask);
    }

    public void uppdate(Task task,Long id) {
        Task newTask = taskRepo.findTaskById(id);
        newTask.setHeader(task.getHeader());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        newTask.setPriority(task.getPriority());
        taskRepo.save(newTask);
    }

    public void addComment(Long taskId, String text){
        Comment newComment = new Comment();
        Task task = taskRepo.findTaskById(taskId);
        newComment.setComment(text);
        newComment.setTask(task);
        task.getComment().add(newComment);
        taskRepo.save(task);
    }


    public Task createTask(CreateDto createDto) {
        Comment comment;

        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username);

        Task task = new Task();
        task.setHeader(createDto.getHeader());
        task.setDescription(createDto.getDescription());
        task.setStatus(createDto.getStatus());
        task.setPriority(createDto.getPriority());

        if (createDto.getComment() != null){
            comment = new Comment();
            comment.setComment(createDto.getComment());
            comment.setTask(task);
            task.getComment().add(comment);
        }
        task.setAuthor(user);
        task.setExecutor(userRepo.findUserById(createDto.getExecutor()));

        return taskRepo.save(task);
    }
}
