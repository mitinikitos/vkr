package com.example.vkr.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    @NotBlank
    private String userName;
    @NotNull
    @Size(min = 5)
    private String password;

}