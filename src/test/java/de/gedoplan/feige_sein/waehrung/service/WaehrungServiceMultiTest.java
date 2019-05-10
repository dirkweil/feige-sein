package de.gedoplan.feige_sein.waehrung.service;

import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.*;

import de.gedoplan.feige_sein.test.base.InContainerIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WaehrungServiceMultiTest extends InContainerIntegrationTest {
  private static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass() {
    // CDI-Container starten
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    // Standard-Kontexte starten
    cdiContainer.getContextControl().startContexts();
  }

  @AfterClass
  public static void afterClass() {
    // CDI-Container stoppen
    cdiContainer.shutdown();
  }

  @Before
  public void before() {
    // Injektionen in this ausführen
    BeanProvider.injectFields(this);

    // Testdaten in DB laden
    loadTestData(TestLevel.WAEHRUNG.ordinal());
  }

  @Inject
  WaehrungService waehrungService;

  @Test
  public void testUmrechnenUSD() {
    BigDecimal fremdBetrag = new BigDecimal(10000);
    Waehrung fremdWaehrung = WaehrungTestDataService.WAEHRUNG_USD;
    String fremdWaehrungId = fremdWaehrung.getId();
    BigDecimal expected = fremdBetrag.multiply(fremdWaehrung.getEuroValue());

    BigDecimal actual = this.waehrungService.umrechnen(fremdBetrag, fremdWaehrungId);
    assertThat("Euro-Betrag", actual, comparesEqualTo(expected));
  }
}
