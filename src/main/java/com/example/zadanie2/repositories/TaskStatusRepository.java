package com.example.zadanie2.repositories;

import com.example.zadanie2.dao.TaskStatus;
import org.springframework.data.repository.CrudRepository;

public interface TaskStatusRepository extends CrudRepository<TaskStatus,Integer> {
    TaskStatus findByStatusName(String name);
}
