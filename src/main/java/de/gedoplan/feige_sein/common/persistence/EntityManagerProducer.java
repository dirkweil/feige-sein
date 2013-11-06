package de.gedoplan.feige_sein.common.persistence;

// CHECKSTYLE:OFF

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer
{
  @PersistenceContext(unitName = "seminar")
  @Produces
  private EntityManager entityManager;
}
