package com.example.vkr.base.controller;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public abstract class BaseController<T, ID> {

    private final BaseService<T, ID> service;

    public BaseController(BaseService<T, ID> service) {
        this.service = service;
    }

    @JsonView(View.UI.class)
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<T> res = service.findAll();

        return ResponseEntity.ok().body(res);
    }

    @JsonView(View.UI.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") ID id) throws EntityNotFoundException {
        T res = service.findById(id);
        return ResponseEntity.ok().body(res);
    }
}
