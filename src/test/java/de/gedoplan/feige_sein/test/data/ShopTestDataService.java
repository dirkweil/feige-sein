package de.gedoplan.feige_sein.test.data;

import de.gedoplan.feige_sein.shop.persistence.Artikel;
import de.gedoplan.feige_sein.shop.persistence.ArtikelRepository;
import de.gedoplan.feige_sein.shop.persistence.Bestellposition;
import de.gedoplan.feige_sein.shop.persistence.Bestellung;
import de.gedoplan.feige_sein.shop.persistence.BestellungRepository;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ShopTestDataService implements TestDataService {
  public static final Artikel ARTIKEL_FLUG_DUS_JFK = new Artikel("Flug DUS-JFK", new BigDecimal("439"), WaehrungTestDataService.WAEHRUNG_EUR);
  public static final Artikel ARTIKEL_HOTEL_MARRIOTT_NYC = new Artikel("Marriott Marquis", new BigDecimal("210"), WaehrungTestDataService.WAEHRUNG_USD);

  private static final long serialVersionUID = 1L;

  @Inject
  EntityManager entityManager;

  @Inject
  ArtikelRepository artikelRepository;

  @Inject
  BestellungRepository bestellungRepository;

  @Override
  public void loadTestData() throws Exception {
    artikelRepository.persist(ARTIKEL_FLUG_DUS_JFK);
    artikelRepository.persist(ARTIKEL_HOTEL_MARRIOTT_NYC);
  }

  @Override
  public void unloadTestData() throws Exception {
    entityManager.createNativeQuery("delete from " + Bestellposition.TABLE_NAME).executeUpdate();
    entityManager.createNativeQuery("delete from " + Bestellung.TABLE_NAME).executeUpdate();
    entityManager.createNativeQuery("delete from " + Artikel.TABLE_NAME).executeUpdate();
  }

  @Override
  public int getLevel() {
    return TestLevel.SHOP.ordinal();
  }

}
