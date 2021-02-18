package com.example.vkr.ship.service.ownoperator;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.ship.model.OwnOperator;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OwnOperatorServiceSaveTest extends OwnOperatorServiceTest {

    @Test
    void saveSuccess() {
        OwnOperator savedOwnOperator = ownOperatorService.save(ownOperator);
        OwnOperator operatorOwn = ownOperatorService.findById(ownOperator.getName());


        assertEquals(operatorOwn.getName(), savedOwnOperator.getName());
        assertEquals(operatorOwn.getEmail(), savedOwnOperator.getEmail());
    }

    @Test
    void saveFailedExists() {
        assertThrows(EntityExistsException.class, () -> ownOperatorService.save(ownOperator2));
    }

    @Test
    void saveFailedNull() {
        assertThrows(BindingException.class, () -> ownOperatorService.save(null));
    }
}
