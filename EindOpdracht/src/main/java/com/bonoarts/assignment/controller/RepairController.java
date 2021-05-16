package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.model.Action;
import com.bonoarts.assignment.model.Repair;
import com.bonoarts.assignment.model.Resource;
import com.bonoarts.assignment.repository.RepairRepository;
import com.bonoarts.assignment.service.RepairService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    // For json
    @Autowired
    private ObjectMapper mapper;

    @GetMapping(value = "")
    public ResponseEntity<Object> getRepairs() {
        return ResponseEntity.ok().body(repairService.getRepairs());
    }

    @GetMapping(value = "/todo")
    public ResponseEntity<Object> getToDoRepairs() {
        return ResponseEntity.ok().body(repairService.getToDoRepairs());
    }

    @GetMapping(value = "/finished")
    public ResponseEntity<Object> getFinishedRepairs() {
        return ResponseEntity.ok().body(repairService.getFinishedRepairs());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getRepair(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(repairService.getRepairById(id));
    }

    @GetMapping(value = "/receipt/{id}")
    public ResponseEntity<Object> getReceipt(@PathVariable("id") Integer id) {
        Repair repair = repairService.getRepairById(id).get();
        if (repair == null) throw new RecordNotFoundException();

        double basePrice = 20.0;
        double allActionsPrice = 0;
        double allResourcesPrice = 0;
        ObjectNode receipt = mapper.createObjectNode();

        // Add the only necessary info from the actions
        ArrayNode cleanActions = mapper.createArrayNode();

        for (Action action: repair.getActions()) {
            allActionsPrice = allActionsPrice + action.getPrice();
            ObjectNode cleanAction = mapper.createObjectNode();

            // Same for resources
            ArrayNode cleanResources = mapper.createArrayNode();

            for (Resource resource: action.getResources()) {
                allResourcesPrice = allResourcesPrice + resource.getPrice();
                ObjectNode cleanResource = mapper.createObjectNode();
                cleanResource.put("Price", action.getPrice());
                cleanResource.put("Name", action.getName());

                cleanResources.insert(0, cleanResource);
            }

            cleanAction.put("resources", cleanResources);
            cleanAction.put("Price", action.getPrice());
            cleanAction.put("Name", action.getName());
            cleanAction.put("Duration", action.getDuration());

            cleanActions.insert(0, cleanAction);
        }

        receipt.put("actions", cleanActions);

        // Calculate the final prices
        double fullPrice = allResourcesPrice + allActionsPrice + basePrice;
        double fullPriceWithBTW = fullPrice / 100 * 121;

        receipt.put("Base Price", basePrice);
        receipt.put("Price", fullPrice);

        receipt.put("Price with BTW", fullPriceWithBTW);

        return ResponseEntity.ok().body(receipt);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createRepair(@RequestBody Repair repair) {
        Integer newId = repairService.createRepair(repair);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PostMapping(value = "denied/{id}")
    public ResponseEntity<Object> deniedRepair(@PathVariable("id") Integer id) {


        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateRepair(@PathVariable("id") Integer id, @RequestBody Repair repair) {
        repairService.updateRepair(id, repair);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateRepairPartial(@PathVariable("id") Integer id, @RequestBody Map<String, Object> fields) {
        repairService.partialUpdateRepair(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteRepair(@PathVariable("id") Integer id) {
        repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }
}
