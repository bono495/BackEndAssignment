package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.repository.ClientsRepository;
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
public class ClientsControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientsRepository clientRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testClients() throws Exception {
        mockMvc.perform(get("/clients")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetClient() throws Exception {
        // Arrange
        Client newClient = new Client();

        // Act
        newClient.setFirst_name("Test");
        newClient.setLast_name("Test");
        newClient.setEmail("Test");
        newClient.setAddress("Test");
        newClient.setPhonenumber("Test");

        Client client = clientRepository.save(newClient);

        // Assert
        mockMvc.perform(get("/clients/" + client.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(client.getId()));
    }

    @Test
    public void testCreateClient() throws Exception {
        ObjectNode client = mapper.createObjectNode();
        client.put("password", "password");

        mockMvc.perform(post("/clients").contentType("application/json").content(String.valueOf(client)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutClient() throws Exception {
        ObjectNode client = mapper.createObjectNode();
        client.put("password", "password");
        client.put("clientName", "newEngineer");
        client.put("email", "newEngineer@bmw.nl");

        mockMvc.perform(put("/clients/1").contentType("application/json").content(String.valueOf(client)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteClient() throws Exception {
        // Arrange
        Client newClient = new Client();

        // Act
        newClient.setFirst_name("Test");
        newClient.setLast_name("Test");
        newClient.setEmail("Test");
        newClient.setAddress("Test");
        newClient.setPhonenumber("Test");
        Client client = clientRepository.save(newClient);

        mockMvc.perform(delete("/clients/" + client.getId()))
                .andExpect(status().isNoContent());
    }

}
