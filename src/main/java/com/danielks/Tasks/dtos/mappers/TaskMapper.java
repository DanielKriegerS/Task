package com.danielks.Tasks.dtos.mappers;

import com.danielks.Tasks.dtos.TaskDTO;
import com.danielks.Tasks.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "header", source = "header")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "ended", source = "ended")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "header", source = "header")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "ended", source = "ended")
    Task taskDTOToTask(TaskDTO taskDTO);
}
