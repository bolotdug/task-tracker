package com.akvelon.tasktracker.dto;

import com.akvelon.tasktracker.models.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private Long id;

    private String name;
    private String description;
    private Integer priority;
    private TaskStatus status;
    private Long projectId;
}
