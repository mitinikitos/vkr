package com.example.vkr.ship.controller;

import com.example.vkr.base.controller.BaseController;
import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.ShipCapacity;
import com.example.vkr.ship.service.ShipCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/shipCapacity")
public class ShipCapacityController extends BaseController<ShipCapacity, Integer> {

    @Autowired
    private ShipCapacityService shipCapacityService;

    /**
     * Creates {@link BaseController} for the given {@link BaseService}
     * @param service must not be {@literal null}
     */
    public ShipCapacityController(BaseService<ShipCapacity, Integer> service) {
        super(service);
    }

    @RequestMapping(value = "/put",
            method = RequestMethod.PUT,
            headers = { "Content-type=application/json" })
    public ResponseEntity<?> updateEntity(@RequestBody @Valid ShipCapacity entity, BindingResult bindingResult)
            throws BindingException, EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            throw new BindingException("Json error");
        }

        ShipCapacity shipCapacity = shipCapacityService.update(entity);
        return ResponseEntity.ok().body(shipCapacity);
    }
}
