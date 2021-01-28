package com.example.vkr.ship.controller;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.UnprocessableEntityException;
import com.example.vkr.ship.service.ShipService;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.TransactionStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ship")
@JsonView(View.UI.class)
public class ShipController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ObjectMapper objectMapper;

    @JsonView(View.UI.class)
    @GetMapping(value = "/{regNum}")
    public ResponseEntity<Ship> read(@PathVariable("regNum") int regNum) throws EntityNotFoundException {

        Optional<Ship> optionalShip = shipService.findById(regNum);
        if (optionalShip.isEmpty()) {
            throw new EntityNotFoundException("id-" + regNum);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(optionalShip.get());
    }

    @PostMapping("/addShips")
    public ResponseEntity<?> addShips(@RequestBody @Valid List<Ship> ships) {
        shipService.saveAll(ships);

        return ResponseEntity.ok("");
    }

//    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/addShip",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @JsonView(View.UI.class)
    public ResponseEntity<?> addShip(@RequestBody @Valid Ship ship, BindingResult bindingResult)
            throws BindingException, EntityExistsException {

        if (bindingResult.hasErrors()){
            throw new BindingException("Error json");
        }

        Ship addShip = shipService.save(ship);

        return ResponseEntity.status(201).body(addShip);
    }

//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{regNum}")
    public ResponseEntity<?> deleteShip(@PathVariable("regNum") int regNum) throws EntityNotFoundException {
        shipService.deleteById(regNum);
        return ResponseEntity.ok().body("delete ship success");
    }

    @RequestMapping(value = "/ships",
            params = {"page", "amount", "sort"},
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getShipsWithSort(@PathParam("page") int page,
                                              @PathParam("amount") int amount,
                                              @PathParam("sort") String sort) {
        List<Ship> shipPage = shipService.findAllWithSort(page, amount, sort);
        return ResponseEntity.ok(shipPage);
    }

    @JsonView(View.UI.class)
    @RequestMapping(value = "/allShip",
            method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public List<Ship> readAll() {
        return shipService.findAll();
    }
}
