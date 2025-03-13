package com.example.taskmanagement.data.repository;

import com.example.taskmanagement.data.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("SELECT T FROM Task T WHERE T.id = :id")
    Task findTaskById(@Param("id") Long id);
}
