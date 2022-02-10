package com.akvelon.tasktracker.service;

import com.akvelon.tasktracker.dto.ProjectDto;
import com.akvelon.tasktracker.exceptions.NotFoundException;
import com.akvelon.tasktracker.mappers.ProjectMapper;
import com.akvelon.tasktracker.mappers.ProjectMapperImpl;
import com.akvelon.tasktracker.models.Project;
import com.akvelon.tasktracker.models.ProjectStatus;
import com.akvelon.tasktracker.repository.ProjectRepository;
import com.akvelon.tasktracker.repository.TaskRepository;
import com.akvelon.tasktracker.service.impl.ProjectServiceImpl;
import com.akvelon.tasktracker.util.DateSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class ProjectServiceTest {

    ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    ProjectMapper projectMapper = Mockito.mock(ProjectMapper.class);
    DateSupplier dateSupplier = new DateSupplier();
    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    ProjectService service = new ProjectServiceImpl(projectRepository, projectMapper, dateSupplier, taskRepository);

    @Test
    void testCreateProject(){
        ProjectDto testProjectDto = createTestProjectDto();
        Project testProject = createTestProject();

        Mockito.when(projectMapper.projectDtoToEntity(testProjectDto)).thenReturn(testProject);
        Mockito.when(projectRepository.save(testProject)).thenReturn(testProject);
        ProjectDto createdProject = service.create(testProjectDto);
        Mockito.verify(projectRepository, times(1)).save(testProject);
        Mockito.verify(projectMapper, times(1)).projectToProjectDto(any());
    }

    private ProjectDto createTestProjectDto() {
        ProjectDto project = new ProjectDto();
        project.setName("ProjectA");
        project.setPriority(1);
        project.setStatus(ProjectStatus.NotStarted);
        return project;
    }

    @Test
    void getProject_shouldThrowException(){
        Optional<Project> optionalProject = Optional.empty();
        Mockito.when(projectRepository.findById(1L)).thenReturn(optionalProject);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.getById(1L),
                "Project with id = 1 not found"
        );
        assertEquals("Project with id = 1 not found", exception.getMessage());
        Mockito.verify(projectMapper, times(0)).projectToProjectDto(any());
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
