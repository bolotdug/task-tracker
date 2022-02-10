package com.akvelon.tasktracker.service;

import com.akvelon.tasktracker.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<ProjectDto> getAllProjectDtos(Pageable pageable);
    ProjectDto getById(Long id);
    ProjectDto updateById(Long id, ProjectDto projectDto);
    ProjectDto create(ProjectDto projectDto);
    void delete(Long id);
}
