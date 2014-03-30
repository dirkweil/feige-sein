package de.gedoplan.feige_sein.waehrung.persistence;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * DB-Zugriffsklasse für {@link Waehrung}.
 *
 * Zur Demonstration sind hier die benötigten CRUD-Methoden ausprogrammiert. Im "echten Leben" lässt sich dafür eleganter eine
 * Basisklasse nutzen (wie angedeutet).
 *
 * @author dw
 *
 */
@Transactional
public class WaehrungRepository //extends SingleIdEntityRepository<String, Waehrung>
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  EntityManager             entityManager;

  public void persist(Waehrung entity)
  {
    this.entityManager.persist(entity);
  }

  public List<Waehrung> findAll()
  {
    return this.entityManager.createQuery("select x from Waehrung x", Waehrung.class).getResultList();
  }

  public Waehrung findById(String id)
  {
    return this.entityManager.find(Waehrung.class, id);
  }

}
