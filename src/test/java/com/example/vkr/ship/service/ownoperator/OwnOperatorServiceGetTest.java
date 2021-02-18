package com.example.vkr.ship.service.ownoperator;

import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.OwnOperator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.BindException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OwnOperatorServiceGetTest extends OwnOperatorServiceTest {

    @Test
    void getSuccess() {
        OwnOperator actual = ownOperatorService.findById(ownOperator2.getName());
        assertEquals(ownOperator2.getEmail(), actual.getEmail());
    }

    @Test
    void getFailedNotExists() {
        assertThrows(EntityNotFoundException.class, () -> ownOperatorService.findById(ownOperator.getName()));
    }

    @Test
    void getFailedNullId() {
        assertThrows(BindingException.class, () -> ownOperatorService.findById(null));
    }
}
