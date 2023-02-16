package com.example.zadanie2.controllers;

import com.example.zadanie2.dtos.ResponseTaskDTO;
import com.example.zadanie2.dtos.TaskDTO;
import com.example.zadanie2.services.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseTaskDTO newTask(@RequestBody TaskDTO taskDTO) {
        return taskService.saveTask(taskDTO);
    }

    @PutMapping
    public ResponseTaskDTO editTask(@RequestParam int id,@RequestBody TaskDTO taskDTO) {
        return taskService.editTask(taskDTO,id);
    }

    @GetMapping
    public List<ResponseTaskDTO> getTasks(@RequestParam(required = false) Integer id,
                                          @RequestParam(required = false) String title,
                                          @RequestParam(required = false) String description,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) OffsetDateTime deadline,
                                          @RequestParam(required = false) String status) {
        return taskService.getTasksFiltered(id,title,description,deadline,status);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam int id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseTaskDTO changeStatus(@PathVariable int id, @RequestParam int statusId) {
        return taskService.changeTaskStatus(id,statusId);
    }

    @PatchMapping("/{id}/users")
    public ResponseTaskDTO addUsersToTask(@PathVariable int id,@RequestBody List<Integer> userIds) {
        return taskService.addUsersToTask(id,userIds);
    }




}
