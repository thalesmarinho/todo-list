package com.thalesmarinho.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

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
    private boolean completed;
}