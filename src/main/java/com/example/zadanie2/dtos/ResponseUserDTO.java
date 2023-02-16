package com.example.zadanie2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserDTO {
    private Integer id;
    private String name;
    private String surname;

    private String email;
}
