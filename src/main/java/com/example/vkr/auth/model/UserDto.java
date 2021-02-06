package com.example.vkr.auth.model;


import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.UI.class)
public class UserDto {
    private String userName;
    private String password;
    private String phone;
    private String email;

    public User getUserFromDto(){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);

        return user;
    }
}
