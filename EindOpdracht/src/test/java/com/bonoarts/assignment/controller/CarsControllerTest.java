package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.AssignmentApplicationTests;
import com.bonoarts.assignment.model.Car;
import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.repository.*;
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
public class CarsControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CarsRepository carRepository;
    @Autowired
    private ClientsRepository clientRepository;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCars() throws Exception {
        mockMvc.perform(get("/cars")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCar() throws Exception {
        // Arrange
        Car newCar = new Car();

        // Act
        Client client = clientRepository.save(new Client());

        newCar.setBrand("Test");
        newCar.setPapers("Testurl");
        newCar.setYear(0000);
        newCar.setClient(client);

        Car car = carRepository.save(newCar);

        // Assert
        mockMvc.perform(get("/cars/" + car.getId())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(car.getId()));
    }

    @Test
    public void testCreateCar() throws Exception {
        ObjectNode car = mapper.createObjectNode();
        Client client = clientRepository.save(new Client());
        ObjectNode testClient = mapper.createObjectNode();
        testClient.put("id", client.getId());

        car.put("brand", "Test");
        car.put("papers", "Testurl");
        car.put("year", 0000);
        car.put("client_id", 1);
        car.put("client", testClient);

        mockMvc.perform(post("/cars").contentType("application/json").content(String.valueOf(car)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutCar() throws Exception {
        ObjectNode car = mapper.createObjectNode();
        Client client = clientRepository.save(new Client());
        ObjectNode testClient = mapper.createObjectNode();
        testClient.put("id", client.getId());

        car.put("password", "password");
        car.put("carName", "newEngineer");
        car.put("email", "newEngineer@bmw.nl");
        car.put("client_id", 1);
//        car.put("client", );

        mockMvc.perform(put("/cars/1").contentType("application/json").content(String.valueOf(car)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCar() throws Exception {
        // Arrange
        Car newCar = new Car();

        // Act
        Client client = clientRepository.save(new Client());

        newCar.setBrand("Test");
        newCar.setPapers("Testurl");
        newCar.setYear(0000);
        newCar.setClient(client);
        Car car = carRepository.save(newCar);

        mockMvc.perform(delete("/cars/" + car.getId()))
                .andExpect(status().isNoContent());
    }
}
