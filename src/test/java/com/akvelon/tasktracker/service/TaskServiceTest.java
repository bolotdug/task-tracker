package com.akvelon.tasktracker.service;

import com.akvelon.tasktracker.dto.TaskDto;
import com.akvelon.tasktracker.exceptions.NotFoundException;
import com.akvelon.tasktracker.mappers.TaskMapper;
import com.akvelon.tasktracker.models.Project;
import com.akvelon.tasktracker.models.ProjectStatus;
import com.akvelon.tasktracker.models.Task;
import com.akvelon.tasktracker.models.TaskStatus;
import com.akvelon.tasktracker.repository.ProjectRepository;
import com.akvelon.tasktracker.repository.TaskRepository;
import com.akvelon.tasktracker.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class TaskServiceTest {

    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    TaskMapper taskMapper = Mockito.mock(TaskMapper.class);

    TaskService service = new TaskServiceImpl(taskRepository, projectRepository, taskMapper);

    @Test
    void testCreateTask(){
        TaskDto testTaskDto = createTestTaskDto();
        Task testTask = createTestTask();
        Project testProject = createTestProject();
        Optional<Project> optionalProject = Optional.of(testProject);

        Mockito.when(taskMapper.taskDtoToEntity(testTaskDto)).thenReturn(testTask);
        Mockito.when(projectRepository.findById(1L)).thenReturn(optionalProject);
        Mockito.when(taskRepository.save(testTask)).thenReturn(testTask);
        TaskDto createdTask = service.create(testTaskDto);
        Mockito.verify(taskRepository, times(1)).save(testTask);
        Mockito.verify(taskMapper, times(1)).taskToTaskDto(any());
    }

    private TaskDto createTestTaskDto() {
        TaskDto taskDto = new TaskDto();
        taskDto.setName("TaskA");
        taskDto.setPriority(1);
        taskDto.setStatus(TaskStatus.ToDo);
        taskDto.setProjectId(1L);
        return taskDto;
    }

    @Test
    void getTask_shouldThrowException(){
        Optional<Task> optionalTask = Optional.empty();
        Mockito.when(taskRepository.findById(1L)).thenReturn(optionalTask);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.getById(1L),
                "Task with id = 1 not found"
        );
        assertEquals("Task with id = 1 not found", exception.getMessage());
        Mockito.verify(taskMapper, times(0)).taskToTaskDto(any());
    }

    private Task createTestTask() {
        Task task = new Task();
        task.setId(1L);
        task.setName("TaskA");
        task.setPriority(1);
        task.setStatus(TaskStatus.ToDo);
        return task;
    }

    private Project createTestProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("ProjectA");
        project.setPriority(1);
        project.setStatus(ProjectStatus.NotStarted);
        return project;
    }
}
