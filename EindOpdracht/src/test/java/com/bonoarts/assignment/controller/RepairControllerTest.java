package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.Repair;
import com.bonoarts.assignment.model.Repair;
import com.bonoarts.assignment.repository.RepairRepository;
import com.bonoarts.assignment.service.RepairService;
import com.bonoarts.assignment.service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepairControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RepairRepository repairRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRepairs() throws Exception {
        mockMvc.perform(get("/repairs")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetRepair() throws Exception {
        // Arrange
        Repair newRepair = new Repair();

        // Act
        newRepair.setStatus("Finished");
        newRepair.setPayed(true);
//        newRepair.setStock(1);
        Repair repair = repairRepository.save(newRepair);

        // Assert
        mockMvc.perform(get("/repairs/" + repair.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(repair.getId()));
    }

    @Test
    public void testCreateRepair() throws Exception {
        ObjectNode repair = mapper.createObjectNode();
        repair.put("password", "password");

        mockMvc.perform(post("/repairs").contentType("application/json").content(String.valueOf(repair)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutRepair() throws Exception {
        ObjectNode repair = mapper.createObjectNode();
        repair.put("password", "password");
        repair.put("repairName", "newEngineer");
        repair.put("email", "newEngineer@bmw.nl");

        mockMvc.perform(put("/repairs/1").contentType("application/json").content(String.valueOf(repair)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteRepair() throws Exception {
        // Arrange
        Repair newRepair = new Repair();

        // Act
        newRepair.setStatus("Finished");
        newRepair.setPayed(true);
        Repair repair = repairRepository.save(newRepair);

        mockMvc.perform(delete("/repairs/" + repair.getId()))
                .andExpect(status().isNoContent());
    }

}
