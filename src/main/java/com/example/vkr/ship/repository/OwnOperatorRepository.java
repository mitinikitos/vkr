package com.example.vkr.ship.repository;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.ship.model.OwnOperator;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OwnOperatorRepository extends BaseRepository<OwnOperator, String> {
}
