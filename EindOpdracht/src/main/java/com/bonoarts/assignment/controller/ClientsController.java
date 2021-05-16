package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/clients")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "")
    public ResponseEntity<Object> searchClients(@RequestParam(name="first_name", defaultValue="") String first_name,
                                              @RequestParam(name="last_name", defaultValue="") String back_end) {
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getClient(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(clientService.getClient(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createClient(@RequestBody Client client) {
        Integer newId = clientService.createClient(client);

        // Create url to new user
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable("id") Integer id, @RequestBody Client client) {
        clientService.updateClient(id, client);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateBookPartial(@PathVariable("id") Integer id, @RequestBody Map<String, String> fields) {
        clientService.partialUpdateClient(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable("id") Integer id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
