package com.example.zadanie2.dtos.converters;

import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dao.User;
import com.example.zadanie2.dtos.UserDTO;

public class UserConverter {
    public static ResponseUserDTO convertToResponseDto(User user) {
        return ResponseUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

    public static User convertToDao(UserDTO dto) {
        return User.builder().name(dto.getName()).surname(dto.getSurname()).email(dto.getEmail()).build();
    }
}
