package com.akvelon.tasktracker.dto;

import com.akvelon.tasktracker.models.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant completionDate;
    private Integer priority;
    private ProjectStatus status;
    private List<TaskDto> tasks = new ArrayList<>();
}
