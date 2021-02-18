package com.example.vkr.ship.service.ownoperator;

import com.example.vkr.AbstractTest;
import com.example.vkr.ship.model.OwnOperator;
import com.example.vkr.ship.repository.OwnOperatorRepository;
import com.example.vkr.ship.service.OwnOperatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OwnOperatorServiceTest extends AbstractTest {

    @Autowired
    protected OwnOperatorService ownOperatorService;
    @Autowired
    protected OwnOperatorRepository ownOperatorRepository;

    protected OwnOperator ownOperator = new OwnOperator("test", "test address", new String[]{""}, "test@mail.ru", new String[]{""});
    protected OwnOperator ownOperator2 = new OwnOperator("test2", "test2 address", new String[]{}, "test2@mail.ru", new String[]{});


    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        resetDb();
        ownOperatorRepository.save(ownOperator2);
    }

    @Override
    @AfterEach
    protected void reset() throws Exception {
        resetDb();
    }

    private void resetDb() {
        delete(ownOperator);
        delete(ownOperator2);
    }

    private void delete(OwnOperator ownOperator) {
        try {
            ownOperatorRepository.deleteById(ownOperator.getName());
        } catch (Exception ignored) {}
    }
}
