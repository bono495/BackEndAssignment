package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.User;
import com.bonoarts.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUser() throws Exception {
        // Arrange
        User newUser = new User();

        // Act
        newUser.setPassword(passwordEncoder.encode("password"));
        newUser.setUserName("test");
        newUser.setEmail("test@test.nl");
        User user = userRepository.save(newUser);

        // Assert
        mockMvc.perform(get("/users/" + user.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testCreateUser() throws Exception {
        ObjectNode user = mapper.createObjectNode();
        user.put("password", "password");

        mockMvc.perform(post("/users").contentType("application/json").content(String.valueOf(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutUser() throws Exception {
        ObjectNode user = mapper.createObjectNode();
        user.put("password", "password");
        user.put("userName", "newEngineer");
        user.put("email", "newEngineer@bmw.nl");

        mockMvc.perform(put("/users/1").contentType("application/json").content(String.valueOf(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        User newUser = new User();

        // Act
        newUser.setPassword(passwordEncoder.encode("password"));
        newUser.setUserName("test");
        newUser.setEmail("test@test.nl");
        User user = userRepository.save(newUser);

        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }
}
