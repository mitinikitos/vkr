package com.example.vkr.ship.service.impl;

import com.example.vkr.ship.model.Engine;
import com.example.vkr.ship.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class EngineServiceImpl implements EngineService {

    @Override
    public Engine save(Engine engine) {
        return null;
    }

    @Override
//    @Transactional
    public List<Engine> saveAll(List<Engine> engines) {
        return null;
    }

    private List<Engine> duplicate(List<Engine> engines) {

        for (int i = 0; i < engines.size(); i ++) {
            Engine engine = findByPwrAndDvigAndCount(engines.get(i));
            if (engine != null) {
                engines.set(i, engine);
            }
        }

        if (isDuplicate(engines.get(0), engines.get(1))) {
            engines.set(1, engines.get(0));
        }
        if (isDuplicate(engines.get(0), engines.get(2))) {
            engines.set(2, engines.get(0));
        } else if (isDuplicate(engines.get(1), engines.get(2))) {
            engines.set(2, engines.get(1));
        }
        return engines;
    }

    private boolean isDuplicate(Engine engine1, Engine engine2) {
        if (engine1 == null || engine2 == null) {
            return false;
        } else {
            return engine1.getCount() == engine2.getCount()
                && engine1.getDvig().equals(engine2.getDvig())
                && engine1.getPwr() == engine2.getPwr();
        }
    }

    @Override
    public Engine findByPwrAndDvigAndCount(Engine engine) {
        return null;
    }
}
