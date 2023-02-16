package com.example.zadanie2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTaskDTO {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private OffsetDateTime deadline;
    private List<ResponseUserDTO> users;

}
