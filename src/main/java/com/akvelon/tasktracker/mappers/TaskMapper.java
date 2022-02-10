package com.akvelon.tasktracker.mappers;

import com.akvelon.tasktracker.dto.TaskDto;
import com.akvelon.tasktracker.models.Project;
import com.akvelon.tasktracker.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "project", target = "projectId")
    TaskDto taskToTaskDto(Task task);

    Task taskDtoToEntity(TaskDto taskDto);
    List<TaskDto> tasksToTaskDtos(List<Task> tasks);
    List<Task> taskDtosToEntities(List<TaskDto> taskDtos);

    default Long map(Project project) {
        if (project == null){
            return 0L;
        }
        return project.getId();
    }
}
