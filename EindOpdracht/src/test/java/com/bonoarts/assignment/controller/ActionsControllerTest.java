package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.Action;
import com.bonoarts.assignment.repository.ActionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionsControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ActionRepository actionRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testActions() throws Exception {
        mockMvc.perform(get("/actions")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetAction() throws Exception {
        // Arrange
        Action newAction = new Action();

        // Act
        newAction.setDuration(1);
        newAction.setName("Test");
        newAction.setPrice(1);

        Action action = actionRepository.save(newAction);

        // Assert
        mockMvc.perform(get("/actions/" + action.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(action.getId()));
    }

    @Test
    public void testCreateAction() throws Exception {
        ObjectNode action = mapper.createObjectNode();
        action.put("duration", 1);
        action.put("name", "Test");
        action.put("price", 1);

        mockMvc.perform(post("/actions").contentType("application/json").content(String.valueOf(action)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutAction() throws Exception {
        ObjectNode action = mapper.createObjectNode();
        action.put("duration", 1);
        action.put("name", "Test");
        action.put("price", 1);

        mockMvc.perform(put("/actions/1").contentType("application/json").content(String.valueOf(action)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAction() throws Exception {
        // Arrange
        Action newAction = new Action();

        // Act
        newAction.setDuration(1);
        newAction.setName("Test");
        newAction.setPrice(1);
        Action action = actionRepository.save(newAction);

        mockMvc.perform(delete("/actions/" + action.getId()))
                .andExpect(status().isNoContent());
    }
}
