package com.example.taskmanagement.data.repository;

import com.example.taskmanagement.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
