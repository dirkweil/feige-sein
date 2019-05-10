package de.gedoplan.feige_sein.test.persistence;

// CHECKSTYLE:OFF

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
@Alternative
@Priority(1)
public class TestEntityManagerProducer {
  EntityManagerFactory entityManagerFactory;

  @PostConstruct
  private void postConstruct() {
    this.entityManagerFactory = Persistence.createEntityManagerFactory("test");
  }

  @Produces
  @RequestScoped
  EntityManager createEntityManager() {
    return this.entityManagerFactory.createEntityManager();
  }

  void disposeEntityManager(@Disposes EntityManager entityManager) {
    entityManager.close();
  }
}
