package com.akvelon.tasktracker.service.impl;

import com.akvelon.tasktracker.dto.TaskDto;
import com.akvelon.tasktracker.exceptions.NotFoundException;
import com.akvelon.tasktracker.mappers.TaskMapper;
import com.akvelon.tasktracker.models.Project;
import com.akvelon.tasktracker.models.Task;
import com.akvelon.tasktracker.repository.ProjectRepository;
import com.akvelon.tasktracker.repository.TaskRepository;
import com.akvelon.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public Page<TaskDto> getAllTaskDtos(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<TaskDto> taskDtos = taskPage.stream()
                .map(task -> taskMapper.taskToTaskDto(task))
                .collect(Collectors.toList());
        return new PageImpl<>(
                taskDtos,
                pageable,
                taskPage.getTotalElements()
        );
    }

    @Override
    public TaskDto getById(Long id) {
        return taskRepository.findById(id)
                .map(task -> taskMapper.taskToTaskDto(task))
                .orElseThrow(() -> new NotFoundException("Task with id = " + id + " not found"));
    }

    @Override
    @Transactional
    public TaskDto updateById(Long id, TaskDto taskDto) {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id = " + id + " not found"));
        taskToUpdate.setName(taskDto.getName());
        taskToUpdate.setDescription(taskDto.getDescription());
        taskToUpdate.setStatus(taskDto.getStatus());
        taskToUpdate.setPriority(taskDto.getPriority());
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project with id = " + taskDto.getProjectId() + " not found"));
        taskToUpdate.setProject(project);
        Task updatedTask = taskRepository.save(taskToUpdate);
        return taskMapper.taskToTaskDto(updatedTask);
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        Task taskToDB = taskMapper.taskDtoToEntity(taskDto);
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project with id = " + taskDto.getProjectId() + " not found"));
        taskToDB.setProject(project);
        Task savedTask = taskRepository.save(taskToDB);
        return taskMapper.taskToTaskDto(savedTask);
    }

    @Override
    public void delete(Long id) {
        Task taskToDeletion = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id = " + id + " not found"));
        taskRepository.delete(taskToDeletion);
    }
}
