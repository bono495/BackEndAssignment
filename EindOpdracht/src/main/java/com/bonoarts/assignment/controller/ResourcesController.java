package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.model.Resource;
import com.bonoarts.assignment.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/resources")
public class ResourcesController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping(value = "")
    public ResponseEntity<Object> searchResources(@RequestParam(name="first_name", defaultValue="") String first_name,
                                              @RequestParam(name="last_name", defaultValue="") String back_end) {
        return ResponseEntity.ok().body(resourceService.getResources());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getResource(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(resourceService.getResource(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createResource(@RequestBody Resource resource) {
        Integer newId = resourceService.createResource(resource);

        // Create url to new user
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateResource(@PathVariable("id") Integer id, @RequestBody Resource resource) {
        resourceService.updateResource(id, resource);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateBookPartial(@PathVariable("id") Integer id, @RequestBody Map<String, String> fields) {
        resourceService.partialUpdateResource(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteResource(@PathVariable("id") Integer id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
