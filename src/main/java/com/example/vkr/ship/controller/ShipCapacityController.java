package com.example.vkr.ship.controller;

import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.service.ShipCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipCapacity")
public class ShipCapacityController {

    @Autowired
    private ShipCapacityService shipCapacityService;

    @PutMapping("/put")
    public ResponseEntity<?> putCapacity(@RequestBody ShipCapacity shipCapacity) throws EntityNotFoundException {
        boolean isPut = shipCapacityService.save(shipCapacity);
        if (isPut) {
            return ResponseEntity.ok("changes to the shipCapacity have been successfully saved");
        }
        return ResponseEntity.badRequest().body("");
    }
}
