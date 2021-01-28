package com.example.vkr.config;

import com.example.vkr.auth.model.LockedToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Configuration
@Log4j2
@EnableRedisRepositories(basePackageClasses = LockedToken.class)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        log.info("=================================================================");
        log.info("redis config : {} : {} ", host, port);
        log.info("=================================================================");

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, LockedToken> redisTemplate() {
        RedisTemplate<String, LockedToken> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
