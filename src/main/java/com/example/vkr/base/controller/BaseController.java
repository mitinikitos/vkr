package com.example.vkr.base.controller;

import com.example.vkr.base.service.BaseService;
import com.example.vkr.exception.BindingException;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Base controller for adding, removing, find by id, find all entities
 */
@RestController
public abstract class BaseController<T, ID> {

    protected final BaseService<T, ID> service;

    public BaseController(BaseService<T, ID> service) {
        this.service = service;
    }


    /**
     * Return all entities
     */
    @JsonView(View.UI.class)
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<T> res = service.findAll();

        return ResponseEntity.ok().body(res);
    }

    /**
     * Return {@link T} by {@link ID}
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if entity not exists
     * @return {@link T} if exists
     */
    @JsonView(View.UI.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") ID id) throws EntityNotFoundException {
        T res = service.findById(id);
        return ResponseEntity.ok().body(res);
    }

    /**
     * Delete entity for the given {@link ID}. Access only to registered users
     * @param id must not be {@literal null}.
     * @exception EntityNotFoundException if entity not exists
     * @return {@link ResponseEntity#ok()}
     */
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") ID id) throws EntityNotFoundException {
        service.deleteById(id);
        return ResponseEntity.ok().body(String.format("Delete entity with id %s success", id));
    }

    /**
     * Create entity.
     * @param entity must not be {@literal null}.
     * @param bindingResult result bilding entity.
     * @method POST
     * @exception BindingException if binding entity has error.
     * @exception EntityExistsException if entity already exists.
     * @return {@link ResponseEntity#status(int 201)}.
     */
    @JsonView(View.UI.class)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = { "Content-type=application/json" })
    public ResponseEntity<?> addEntity(@RequestBody @Valid T entity, BindingResult bindingResult)
            throws BindingException, EntityExistsException {

        if (bindingResult.hasErrors()) {
            throw new BindingException("Error in Json");
        }

        T saveEntity = service.save(entity);

        return ResponseEntity.status(201).body(saveEntity);
    }
}
