package com.example.zadanie2.dao;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @OneToOne
    private TaskStatus status;
    private OffsetDateTime deadline;
    @OneToMany(mappedBy = "task")
    private List<User> users;

}
