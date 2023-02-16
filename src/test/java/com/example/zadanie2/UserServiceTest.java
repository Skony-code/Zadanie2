package com.example.zadanie2;

import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.UserDTO;
import com.example.zadanie2.dao.User;
import com.example.zadanie2.repositories.UserRepository;
import com.example.zadanie2.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-service-integration-test.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        UserDTO user = UserDTO.builder().name("Jan").surname("Kowalski").email("j.kowalski@gmail.com").build();
        ResponseUserDTO endUser = userService.saveUser(user);
        Assertions.assertEquals(1,endUser.getId());
        Assertions.assertEquals(user.getName(),endUser.getName());
        Assertions.assertEquals(user.getSurname(),endUser.getSurname());
        Assertions.assertEquals(user.getEmail(),endUser.getEmail());
    }

    @Test
    public void deleteUser() {
        User user = User.builder().name("Jan").surname("Kowalski").email("j.kowalski@gmail.com").build();
        userRepository.save(user);

        userService.deleteUser(1);

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(0,users.size());
    }

    @Test
    public void getUsersByIdAndNameAndSurnameAndEmail() {
        User user = User.builder().name("Jan").surname("Kowalski").email("j.kowalski@gmail.com").build();
        userRepository.save(user);
        User user2 = User.builder().name("Adam").surname("Nowak").email("a.nowak@gmail.com").build();
        userRepository.save(user2);

        List<ResponseUserDTO> endUsers = userService.getUsersByIdAndNameAndSurnameAndEmail(null,null,null,null);
        List<ResponseUserDTO> endUsers2 = userService.getUsersByIdAndNameAndSurnameAndEmail(null,null,"NOT KOWALSKI",null);
        List<ResponseUserDTO> endUsers3 = userService.getUsersByIdAndNameAndSurnameAndEmail(null,null,"Kowalski",null);

        Assertions.assertEquals(2,endUsers.size());
        Assertions.assertEquals(0,endUsers2.size());
        Assertions.assertEquals(1,endUsers3.size());
    }
}