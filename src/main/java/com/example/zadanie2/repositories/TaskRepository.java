package com.example.zadanie2.repositories;

import com.example.zadanie2.dao.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
