package com.akvelon.tasktracker.service;

import com.akvelon.tasktracker.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {
     Page<TaskDto> getAllTaskDtos(Pageable pageable);
     TaskDto getById(Long id);
     TaskDto updateById(Long id, TaskDto taskDto);
     TaskDto create(TaskDto taskDto);
     void delete(Long id);
}
