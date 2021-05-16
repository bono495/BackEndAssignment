package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.Resource;
import com.bonoarts.assignment.repository.ResourcesRepository;
import com.bonoarts.assignment.repository.ResourcesRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourcesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ResourcesRepository resourceRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testResources() throws Exception {
        mockMvc.perform(get("/resources")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetResource() throws Exception {
        // Arrange
        Resource newResource = new Resource();

        // Act
        newResource.setName("Part");
        newResource.setStock(1);
        newResource.setStock(1);
        Resource resource = resourceRepository.save(newResource);

        // Assert
        mockMvc.perform(get("/resources/" + resource.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(resource.getId()));
    }

    @Test
    public void testCreateResource() throws Exception {
        ObjectNode resource = mapper.createObjectNode();
        resource.put("password", "password");

        mockMvc.perform(post("/resources").contentType("application/json").content(String.valueOf(resource)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutResource() throws Exception {
        ObjectNode resource = mapper.createObjectNode();
        resource.put("password", "password");
        resource.put("resourceName", "newEngineer");
        resource.put("email", "newEngineer@bmw.nl");

        mockMvc.perform(put("/resources/1").contentType("application/json").content(String.valueOf(resource)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteResource() throws Exception {
        // Arrange
        Resource newResource = new Resource();

        // Act
        newResource.setName("test");
        newResource.setPrice(1);
        newResource.setStock(1);
        Resource resource = resourceRepository.save(newResource);

        mockMvc.perform(delete("/resources/" + resource.getId()))
                .andExpect(status().isNoContent());
    }
}
