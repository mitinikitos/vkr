package com.example.vkr.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@RedisHash(value = "LockedToken")
@Data
@NoArgsConstructor
public class LockedToken implements Serializable {

    public static final long SerialVersionUID = 1L;

    @Indexed
    private String token;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public LockedToken(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}