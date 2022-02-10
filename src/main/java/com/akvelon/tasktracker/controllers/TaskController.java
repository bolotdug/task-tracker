package com.akvelon.tasktracker.controllers;

import com.akvelon.tasktracker.dto.TaskDto;
import com.akvelon.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskDto> getAllTaskDtos(Pageable pageable){
        return taskService.getAllTaskDtos(pageable);
    }

    @GetMapping("/{id}")
    public TaskDto getTaskDtoById(@PathVariable Long id){
        return taskService.getById(id);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto TaskDto){
        return taskService.create(TaskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Long id){
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskService.updateById(id, taskDto);
    }
}
