package com.example.vkr.ship.service.ownoperator;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.model.Ship;
import com.example.vkr.ship.service.ShipService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class OwnOperatorServiceDeleteTest extends OwnOperatorServiceTest {

    @Autowired
    private ShipService shipService;
    private Ship ship;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        ship = new Ship(1, "name", 123, 2019);
        ship.setOwn(ownOperator2);
        ship.setOperator(ownOperator2);
        ownOperator2.setShipsOwn(List.of(ship));
        ownOperator2.setShipsOperator(List.of(ship));
        super.setUp();
    }

    @Test
    void deleteSuccess() {
        ownOperatorService.deleteById(ownOperator2.getName());

        assertThrows(EntityNotFoundException.class, () -> ownOperatorService.findById(ownOperator2.getName()));
        assertThrows(EntityNotFoundException.class, () -> shipService.findById(ship.getId()));
    }

    @Test
    void deleteFailedNotFound() {
        assertThrows(EntityNotFoundException.class, () -> ownOperatorService.deleteById(ownOperator.getName()));
    }

    @Test
    void deleteFailedNullId() {
        assertThrows(BindingException.class, () -> ownOperatorService.deleteById(null));
    }
}
