package com.thalesmarinho.todolist.repository;

import com.thalesmarinho.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findById(final long id);
    List<Task> findTasksByUserId(final Long id);

}