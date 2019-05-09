package de.gedoplan.feige_sein.waehrung.persistence;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
@Transactional
public class WaehrungRepository // extends SingleIdEntityRepository<String, Waehrung>
    implements Serializable {
  private static final long serialVersionUID = 1L;

  @Inject
  EntityManager entityManager;

  public void persist(Waehrung entity) {
    entityManager.persist(entity);
  }

  public List<Waehrung> findAll() {
    return entityManager.createQuery("select x from Waehrung x", Waehrung.class).getResultList();
  }

  public Waehrung findById(String id) {
    return entityManager.find(Waehrung.class, id);
  }

}
