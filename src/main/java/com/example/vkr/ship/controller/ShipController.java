package com.example.vkr.ship.controller;

import com.example.vkr.base.controller.BaseController;
import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.model.ShipDto;
import com.example.vkr.ship.service.ShipService;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/ship")
@JsonView(View.UI.class)
public class ShipController extends BaseController<Ship, Integer> {

    @Autowired
    private ShipService shipService;

    /**
     * Creates {@link ShipController} for the given {@link BaseService}
     * @param service must not be {@literal null}.
     */
    public ShipController(BaseService<Ship, Integer> service) {
        super(service);
    }

    /**
     * @param page must not be {@literal null}. This is the offset of the query
     * @param size must not be {@literal null}. This is the limit of the query
     * @param sort must not be {@literal null}. This is type sort
     * @return {@link List }
     */
    @RequestMapping(value = "",
            params = {"page", "size", "sort"},
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getShipsWithSort(@PathParam("page") int page,
                                              @PathParam("size") int size,
                                              @PathParam("sort") String sort) {
        List<Ship> shipPage = shipService.findAllWithSort(page, size, sort);
        return ResponseEntity.ok(shipPage);
    }

    @JsonView(View.UI.class)
    @GetMapping("/all/{id}")
    public ResponseEntity<?> getShipWithJoin(
            @PathVariable("id") int id,
            @RequestParam(value = "capacity", required = false, defaultValue = "false") Boolean capacity,
            @RequestParam(value = "dimensions", required = false, defaultValue = "false") Boolean dimensions,
            @RequestParam(value = "engines", required = false, defaultValue = "false") Boolean engines)
            throws EntityNotFoundException {

        ShipDto shipDto = shipService.findByIdWithJoin(id, capacity, dimensions, engines);
        return ResponseEntity.ok().body(shipDto);
    }
}
