package com.example.vkr.auth.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.UI.class)
public class AuthToken implements Serializable {

    public static final long SerialVersionUID = 1L;
    @JsonView(View.REST.class)
    private String id;
    private String accessToken;
    private String refreshToken;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;
    private Long exp;

    public AuthToken(String accessToken, Long expiration) {
        this.accessToken = accessToken;
        this.expiration = expiration;
    }

    public AuthToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
