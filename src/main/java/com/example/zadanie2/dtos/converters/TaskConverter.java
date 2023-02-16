package com.example.zadanie2.dtos.converters;

import com.example.zadanie2.dao.Task;
import com.example.zadanie2.dao.TaskStatus;
import com.example.zadanie2.dtos.ResponseTaskDTO;
import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class TaskConverter {
    public static ResponseTaskDTO convertToDto(Task task) {
        List<ResponseUserDTO> userDTOS = new ArrayList<>();
        if (task.getUsers() != null)
            userDTOS = task.getUsers().stream().map(UserConverter::convertToResponseDto).toList();
        return ResponseTaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus().getStatusName())
                .users(userDTOS).build();
    }

    public static Task convertToDao(TaskDTO dto, TaskStatus status) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .status(status).build();
    }
}
