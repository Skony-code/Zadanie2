package com.example.zadanie2;

import com.example.zadanie2.dtos.ResponseUserDTO;
import com.example.zadanie2.dtos.UserDTO;
import com.example.zadanie2.dao.User;
import com.example.zadanie2.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-controller-integration-test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void newUserTest() throws Exception {
        UserDTO user = UserDTO.builder().name("Jan").surname("Kowalski").email("j.kowalski@gmail.com").build();

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        User user1 = userRepository.findById(1).orElseThrow();
        Assertions.assertEquals(user.getName(),user1.getName());
        Assertions.assertEquals(user.getSurname(),user1.getSurname());
        Assertions.assertEquals(user.getEmail(),user1.getEmail());
    }

    @Test
    void newUserNotValidEmailTest() throws Exception {
        UserDTO user = UserDTO.builder().name("Jan").surname("Kowalski").email("aaaaaaaaa").build();

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserUnfilteredTest() throws Exception{
        insertTestUsers();

        String content = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<ResponseUserDTO> users= objectMapper.readValue(content, new TypeReference<List<ResponseUserDTO>>(){});

        Assertions.assertEquals(2,users.size());
    }
    @Test
    void getUserFilteredTest() throws Exception{
        insertTestUsers();

        String content = mockMvc.perform(get("/users")
                        .param("surname","Kowalski"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<ResponseUserDTO> users= objectMapper.readValue(content, new TypeReference<List<ResponseUserDTO>>(){});

        Assertions.assertEquals(1,users.size());
        Assertions.assertEquals("Kowalski",users.get(0).getSurname());
    }

    @Test
    void deleteUserThatExistsTest() throws Exception {
        insertTestUsers();

        mockMvc.perform(delete("/users")
                        .param("id","1"))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(1,users.size());
        Assertions.assertEquals("Adam",users.get(0).getName());
    }

    @Test
    void deleteUserThatNotExistTest() throws Exception {
        insertTestUsers();

        mockMvc.perform(delete("/users")
                        .param("id","3"))
                .andExpect(status().isNotFound());

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(2,users.size());
    }


    private void insertTestUsers() {
        User user = User.builder().name("Jan").surname("Kowalski").email("j.kowalski@gmail.com").build();
        userRepository.save(user);
        User user2 = User.builder().name("Adam").surname("Nowak").email("a.nowak@gmail.com").build();
        userRepository.save(user2);
    }
}
