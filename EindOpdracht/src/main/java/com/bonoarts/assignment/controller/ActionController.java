package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.model.Action;
import com.bonoarts.assignment.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/actions")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getActions() {
        return ResponseEntity.ok().body(actionService.getActions());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAction(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(actionService.getAction(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createAction(@RequestBody Action action) {
        Integer newId = actionService.createAction(action);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAction(@PathVariable("id") Integer id, @RequestBody Action action) {
        actionService.updateAction(id, action);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateActionPartial(@PathVariable("id") Integer id, @RequestBody Map<String, String> fields) {
        actionService.partialUpdateAction(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAction(@PathVariable("id") Integer id) {
        actionService.deleteAction(id);
        return ResponseEntity.noContent().build();
    }
}
