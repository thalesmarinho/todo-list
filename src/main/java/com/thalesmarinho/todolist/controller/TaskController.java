package com.thalesmarinho.todolist.controller;

import com.thalesmarinho.todolist.dto.TaskDto;
import com.thalesmarinho.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto data) {
        return new ResponseEntity<>(taskService.save(data), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        taskService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}