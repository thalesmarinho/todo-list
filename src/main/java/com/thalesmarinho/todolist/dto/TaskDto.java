package com.thalesmarinho.todolist.dto;

import com.thalesmarinho.todolist.model.RecurrenceType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {

    private final long id;

    private UserDto user;

    @NotBlank
    private String title;

    @Column(columnDefinition = "VARCHAR(2048)")
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;

    private LocalDateTime endDate;

}