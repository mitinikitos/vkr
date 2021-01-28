package com.example.vkr.ship.repository;

import com.example.vkr.ship.model.OwnOperator;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface OwnOperatorRepository extends BaseRepository<OwnOperator, String> {
}
