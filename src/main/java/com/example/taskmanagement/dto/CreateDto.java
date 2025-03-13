package com.example.taskmanagement.dto;

import com.example.taskmanagement.data.enums.Priority;
import com.example.taskmanagement.data.enums.Status;
import lombok.Data;

@Data
public class CreateDto {
    private String header;
    private String description;
    private Status status;
    private Priority priority;
    private String comment;
    private Long executor;
}
