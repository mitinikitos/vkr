package com.example.vkr.ship.service;

import com.example.vkr.ship.model.Engine;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public interface EngineService {
    Engine save(Engine engine);
    List<Engine> saveAll(List<Engine> engines);
    Engine findByPwrAndDvigAndCount(Engine engine);
}
