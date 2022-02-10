package com.akvelon.tasktracker.service.impl;

import com.akvelon.tasktracker.dto.ProjectDto;
import com.akvelon.tasktracker.dto.TaskDto;
import com.akvelon.tasktracker.exceptions.NotFoundException;
import com.akvelon.tasktracker.mappers.ProjectMapper;
import com.akvelon.tasktracker.models.Project;
import com.akvelon.tasktracker.models.ProjectStatus;
import com.akvelon.tasktracker.models.Task;
import com.akvelon.tasktracker.repository.ProjectRepository;
import com.akvelon.tasktracker.repository.TaskRepository;
import com.akvelon.tasktracker.service.ProjectService;
import com.akvelon.tasktracker.util.DateSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final DateSupplier dateSupplier;
    private final TaskRepository taskRepository;

    @Override
    public Page<ProjectDto> getAllProjectDtos(Pageable pageable) {
        Page<Project> projectPage =  projectRepository.findAll(pageable);
        List<ProjectDto> projectDtos = projectPage.stream()
                .map(project -> projectMapper.projectToProjectDto(project))
                .collect(Collectors.toList());
        return new PageImpl<>(
                projectDtos,
                pageable,
                projectPage.getTotalElements()
        );
    }

    @Override
    public ProjectDto getById(Long id) {
        return projectRepository.findById(id)
                .map(project -> projectMapper.projectToProjectDto(project))
                .orElseThrow(() -> new NotFoundException("Project with id = " + id + " not found"));
    }

    @Override
    @Transactional
    public ProjectDto updateById(Long id, ProjectDto projectDto) {
        Project projectToUpdate = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id = " + id + " not found"));
        projectToUpdate.setPriority(projectDto.getPriority());
        projectToUpdate.setName(projectDto.getName());

        handleStatus(projectDto, projectToUpdate);
        deleteTasksFromProject(projectToUpdate);
        addTasks(projectDto, projectToUpdate);
        Project updatedProject = projectRepository.save(projectToUpdate);
        return projectMapper.projectToProjectDto(updatedProject);
    }

    private void deleteTasksFromProject(Project projectToUpdate) {
        List<Task> projectTasks = taskRepository.findAll().stream()
                .filter(task -> projectToUpdate.equals(task.getProject()))
                .peek(task -> task.setProject(null))
                .collect(Collectors.toList());
        taskRepository.saveAll(projectTasks);
    }

    private void addTasks(ProjectDto projectDto, Project projectToUpdate) {
        List<TaskDto> taskDtos = projectDto.getTasks();
        if (!taskDtos.stream()
                .allMatch(taskDto -> taskRepository.existsById(taskDto.getId()))){
            throw new NotFoundException("One or more tasks id not found");
        }
        List<Task> changedTasks = new ArrayList<>();
        for(TaskDto taskDto : taskDtos){
            Task taskToUpdate = taskRepository.getById(taskDto.getId());
            taskToUpdate.setProject(projectToUpdate);
            changedTasks.add(taskRepository.save(taskToUpdate));
        }
        projectToUpdate.setTasks(changedTasks);
    }

    private void handleStatus(ProjectDto projectDto, Project projectToUpdate) {
        ProjectStatus currentStatus = projectToUpdate.getStatus();
        ProjectStatus newStatus = projectDto.getStatus();
        if (!currentStatus.equals(newStatus)) {
            if (newStatus == ProjectStatus.Active && currentStatus == ProjectStatus.NotStarted) {
                projectToUpdate.setStartDate(dateSupplier.getInstant());
            }
            if (newStatus == ProjectStatus.Completed) {
                projectToUpdate.setCompletionDate(dateSupplier.getInstant());
            }
            projectToUpdate.setStatus(newStatus);
        }
    }

    @Override
    @Transactional
    public ProjectDto create(ProjectDto projectDto) {
        Project projectToDB = projectMapper.projectDtoToEntity(projectDto);

        Project savedProject = projectRepository.save(projectToDB);
        return projectMapper.projectToProjectDto(savedProject);
    }

    @Override
    public void delete(Long id) {
        Project projectToDeletion = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id = " + id + " not found"));
        projectRepository.delete(projectToDeletion);
    }
}
