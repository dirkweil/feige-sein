package de.gedoplan.feige_sein.test.data;

import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.persistence.WaehrungRepository;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class WaehrungTestDataService implements TestDataService {
  public static final Waehrung WAEHRUNG_EUR = new Waehrung("EUR", new BigDecimal("1.0000"));
  public static final Waehrung WAEHRUNG_USD = new Waehrung("USD", new BigDecimal("0.8341"));

  private static final long serialVersionUID = 1L;

  @Inject
  EntityManager entityManager;

  @Inject
  WaehrungRepository waehrungRepository;

  @Override
  public void loadTestData() throws Exception {
    waehrungRepository.persist(WAEHRUNG_EUR);
    waehrungRepository.persist(WAEHRUNG_USD);
  }

  @Override
  public void unloadTestData() throws Exception {
    entityManager.createNativeQuery("delete from " + Waehrung.TABLE_NAME).executeUpdate();
  }

  @Override
  public int getLevel() {
    return TestLevel.WAEHRUNG.ordinal();
  }

}
