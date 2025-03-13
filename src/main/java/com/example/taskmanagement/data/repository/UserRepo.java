package com.example.taskmanagement.data.repository;

import com.example.taskmanagement.data.entity.User;
import com.example.taskmanagement.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT U FROM User U WHERE :role MEMBER OF U.roles")
    List<User> findExecutorUsers(@Param("role") String role);

    @Query("SELECT U FROM User U WHERE U.id = :id")
    User findUserById(@Param("id")Long id);

}
