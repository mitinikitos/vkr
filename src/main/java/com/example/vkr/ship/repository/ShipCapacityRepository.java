package com.example.vkr.ship.repository;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.ship.model.ShipCapacity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ShipCapacityRepository extends BaseRepository<ShipCapacity, Integer> {
}
