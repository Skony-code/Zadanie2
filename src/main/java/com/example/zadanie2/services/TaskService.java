package com.example.zadanie2.services;

import com.example.zadanie2.dao.Task;
import com.example.zadanie2.dao.User;
import com.example.zadanie2.dtos.ResponseTaskDTO;
import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.TaskDTO;
import com.example.zadanie2.dtos.converters.TaskConverter;
import com.example.zadanie2.dtos.converters.UserConverter;
import com.example.zadanie2.exceptions.StatusNotFoundException;
import com.example.zadanie2.exceptions.TaskNotFoundException;
import com.example.zadanie2.exceptions.UserNotFoundException;
import com.example.zadanie2.repositories.TaskRepository;
import com.example.zadanie2.repositories.TaskStatusRepository;
import com.example.zadanie2.repositories.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskStatusRepository statusRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, TaskStatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
    }

    public ResponseTaskDTO saveTask(TaskDTO dto) {
        Task task = taskRepository.save(TaskConverter.convertToDao(dto, statusRepository.findById(
                        dto.getStatus()).orElseThrow(StatusNotFoundException::new)));
        return TaskConverter.convertToDto(task);
    }

    public ResponseTaskDTO editTask(TaskDTO dto,int id) {
        if(!taskRepository.existsById(id))
            throw new TaskNotFoundException();
        Task dao = TaskConverter.convertToDao(dto,statusRepository.findById(dto.getStatus()).orElseThrow(StatusNotFoundException::new));
        dao.setId(id);
        Task response = taskRepository.save(dao);
        return TaskConverter.convertToDto(response);
    }

    public void deleteTask(int id) {
        if(taskRepository.existsById(id))
            taskRepository.deleteById(id);
        else
            throw new TaskNotFoundException();
    }

    public List<ResponseTaskDTO> getTasksFiltered(Integer id,String title, String desc, OffsetDateTime deadline, String statusName) {
        return taskRepository.findAll(Example.of(new Task(id,title,desc,statusRepository.findByStatusName(statusName),deadline,null)))
                .stream().map(TaskConverter::convertToDto).toList();
    }

    public ResponseTaskDTO changeTaskStatus(int taskId, int statusId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.setStatus(statusRepository.findById(statusId)
                .orElseThrow(StatusNotFoundException::new));

        return TaskConverter.convertToDto(taskRepository.save(task));
    }

    public ResponseTaskDTO addUsersToTask(int taskId,List<Integer> userIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        List<User> taskUsers = task.getUsers();

        List<User> usersToAdd = userRepository.findAllById(userIds).stream().filter(u->!taskUsers.contains(u)).toList();

        taskUsers.addAll(usersToAdd);
        task.setUsers(taskUsers);
        return TaskConverter.convertToDto(taskRepository.save(task));
    }
}
