package com.akvelon.tasktracker.mappers;

import com.akvelon.tasktracker.dto.ProjectDto;
import com.akvelon.tasktracker.models.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto projectToProjectDto(Project project);
    Project projectDtoToEntity(ProjectDto projectDto);
    List<ProjectDto> projectsToProjectDtos(List<Project> projects);
}
