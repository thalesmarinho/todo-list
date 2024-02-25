package com.thalesmarinho.todolist.service;

import com.thalesmarinho.todolist.dto.TaskDto;
import com.thalesmarinho.todolist.mapper.TaskMapper;
import com.thalesmarinho.todolist.model.Task;
import com.thalesmarinho.todolist.model.User;
import com.thalesmarinho.todolist.repository.TaskRepository;
import com.thalesmarinho.todolist.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDto> findAll() {
        return taskRepository.findAll().stream().map(TaskMapper.INSTANCE::toDto).toList();
    }

    public TaskDto findById(long id) {
        return TaskMapper.INSTANCE.toDto(taskRepository.findById(id));
    }

    public TaskDto save(TaskDto data) {
        Task toSave = TaskMapper.INSTANCE.toTask(data);

        Optional<User> maybeLoggedInUser = SecurityUtils.getCurrentUsername();

        maybeLoggedInUser.ifPresent(toSave::setUser);

        return TaskMapper.INSTANCE.toDto(taskRepository.save(toSave));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}