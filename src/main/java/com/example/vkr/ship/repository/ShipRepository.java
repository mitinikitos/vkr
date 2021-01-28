package com.example.vkr.ship.repository;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.ship.model.Ship;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ShipRepository extends BaseRepository<Ship, Integer> {
}
