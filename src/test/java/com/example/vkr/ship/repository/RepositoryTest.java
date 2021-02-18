package com.example.vkr.ship.repository;

import com.example.vkr.AbstractTest;
import com.example.vkr.config.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepositoryTest extends AbstractTest {

    @PersistenceContext(unitName = "entityManagerFactory")
//    @Autowired
    protected EntityManager em;

//    protected void setUp() throws Exception {}
//    protected void reset() throws Exception {}

//    @Autowired
//    protected TestEntityManager em;
}
