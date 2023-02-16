package com.example.zadanie2.controllers;

import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.UserDTO;
import com.example.zadanie2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseUserDTO newUser(@RequestBody @Valid UserDTO user) {
        return userService.saveUser(user);
    }

    @GetMapping()
    public List<ResponseUserDTO> getUsers(@RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String surname,
                               @RequestParam(required = false) String email) {
        return userService.getUsersByIdAndNameAndSurnameAndEmail(id,name,surname,email);
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam int id) {
         userService.deleteUser(id);
    }

}
