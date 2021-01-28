package com.example.vkr.ship.repository;

import com.example.vkr.ship.model.Ship;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface ShipRepository extends BaseRepository<Ship, Integer> {
}
