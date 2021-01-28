package com.example.vkr.auth.controller;

import com.example.vkr.auth.model.User;
import com.example.vkr.auth.repository.AuthRepository;
import com.example.vkr.auth.service.AdminService;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@JsonView(View.UI.class)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/allUser")
    public ResponseEntity<List<User>> findAllUser() {
        List<User> users = adminService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @JsonView(View.UI.class)
    @GetMapping("/user/{userName}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("userName") String userName)
            throws EntityNotFoundException {
        User user = adminService.findByUserName(userName);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/user/{userName}")
    public ResponseEntity<?> setAdminRole(@PathVariable("userName") String userName)
            throws EntityNotFoundException {
        User user = adminService.setAdminRole(userName);
        return ResponseEntity.ok().body(user);
    }
}
