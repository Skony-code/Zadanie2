package com.example.zadanie2.services;

import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.UserDTO;
import com.example.zadanie2.dtos.converters.UserConverter;
import com.example.zadanie2.exceptions.UserNotFoundException;
import com.example.zadanie2.dao.User;
import com.example.zadanie2.repositories.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.zadanie2.dtos.converters.UserConverter.convertToDao;
import static com.example.zadanie2.dtos.converters.UserConverter.convertToResponseDto;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseUserDTO saveUser(UserDTO dto) {
        User user = convertToDao(dto);
        return convertToResponseDto(userRepository.save(user));
    }

    public List<ResponseUserDTO> getUsersByIdAndNameAndSurnameAndEmail(Integer id, String name, String surname, String email) {
        return userRepository.findAll(Example.of(User.builder().id(id).name(name).surname(surname).email(email).build())).stream().map(UserConverter::convertToResponseDto).toList();
    }

    public void deleteUser(int id) {
        if(userRepository.existsById(id))
            userRepository.deleteById(id);
        else
            throw new UserNotFoundException();
    }
}
