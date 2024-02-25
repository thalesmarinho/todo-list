package com.thalesmarinho.todolist.mapper;

import com.thalesmarinho.todolist.dto.TaskDto;
import com.thalesmarinho.todolist.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper  {

    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public abstract Task toTask(TaskDto data);
    public abstract TaskDto toDto(Task task);
}
