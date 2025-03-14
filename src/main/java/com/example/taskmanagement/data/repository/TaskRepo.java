package com.example.taskmanagement.data.repository;

import com.example.taskmanagement.data.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Page<Task> findAll(Pageable pageable);
    @Query("SELECT T FROM Task T WHERE T.id = :id")
    Task findTaskById(@Param("id") Long id);

    Page<Task> findTaskByExecutor_Id(Long id,Pageable pageable);

    Page<Task> findTaskByAuthor_Id(Long authorId,Pageable pageable);
}
