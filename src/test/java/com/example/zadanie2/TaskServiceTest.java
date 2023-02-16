package com.example.zadanie2;

import com.example.zadanie2.dao.Task;
import com.example.zadanie2.dao.TaskStatus;
import com.example.zadanie2.dtos.ResponseTaskDTO;
import com.example.zadanie2.dtos.TaskDTO;
import com.example.zadanie2.repositories.TaskRepository;
import com.example.zadanie2.repositories.TaskStatusRepository;
import com.example.zadanie2.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-service-integration-test.properties")
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStatusRepository statusRepository;

    @Test
    public void saveTaskTest() {
        TaskStatus status = TaskStatus.builder().statusName("To Do").build();
        statusRepository.save(status);
        TaskDTO dto = TaskDTO.builder().title("Zadanie 1").description("Opis zadania 1").deadline(OffsetDateTime.now()).status(1).build();

        ResponseTaskDTO response = taskService.saveTask(dto);


        Assertions.assertEquals(dto.getTitle(),response.getTitle());
        Assertions.assertEquals(dto.getDescription(),response.getDescription());
        Assertions.assertEquals(dto.getDeadline(),response.getDeadline());
        Assertions.assertEquals(status.getStatusName(),response.getStatus());
    }

    @Test
    public void editTaskTest() {
        TaskStatus status = TaskStatus.builder().statusName("To Do").build();
        status = statusRepository.save(status);
        Task task = Task.builder().title("Zadanie 1").description("Opis zadania 1").deadline(OffsetDateTime.now()).status(status).build();
        taskRepository.save(task);

        TaskDTO dto = TaskDTO.builder().title("Zadanie 1 EDYTOWANE").description("Opis zadania EDYTOWANE").deadline(OffsetDateTime.now()).status(1).build();
        ResponseTaskDTO response = taskService.editTask(dto,1);

        Assertions.assertEquals(1,response.getId());
        Assertions.assertEquals(dto.getTitle(),response.getTitle());
        Assertions.assertEquals(dto.getDescription(),response.getDescription());
        Assertions.assertEquals(dto.getDeadline(),response.getDeadline());
        Assertions.assertEquals(status.getStatusName(),response.getStatus());
    }

    @Test
    public void deleteTask() {
        TaskStatus status = TaskStatus.builder().statusName("To Do").build();
        status = statusRepository.save(status);
        Task task = Task.builder().title("Zadanie 1").description("Opis zadania 1").deadline(OffsetDateTime.now()).status(status).build();
        taskRepository.save(task);

        taskService.deleteTask(1);

        List<Task> tasks = taskRepository.findAll();
        Assertions.assertEquals(0,tasks.size());
    }
}
