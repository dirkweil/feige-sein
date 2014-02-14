package de.gedoplan.feige_sein.waehrung.service;

import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.*;

import de.gedoplan.feige_sein.test.base.InContainerIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WaehrungServiceMultiTest extends InContainerIntegrationTest
{
  private static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass()
  {
    // CDI-Container starten
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    // Standard-Kontexte starten
    ContextControl contextControl = cdiContainer.getContextControl();
    contextControl.startContext(RequestScoped.class);
    contextControl.startContext(SessionScoped.class);
    contextControl.startContext(ConversationScoped.class);
    contextControl.startContext(ApplicationScoped.class);
  }

  @AfterClass
  public static void afterClass()
  {
    cdiContainer.shutdown();
  }

  @Before
  public void before()
  {
    if (this.waehrungService == null)
    {
      BeanProvider.injectFields(this);
    }

    loadTestData(TestLevel.WAEHRUNG.ordinal());
  }

  @Inject
  WaehrungService waehrungService;

  @Test
  public void testUmrechnenUSD()
  {
    BigDecimal fremdBetrag = new BigDecimal(10000);
    Waehrung fremdWaehrung = WaehrungTestDataService.WAEHRUNG_USD;
    String fremdWaehrungId = fremdWaehrung.getId();
    BigDecimal expected = fremdBetrag.multiply(fremdWaehrung.getEuroValue());

    BigDecimal actual = this.waehrungService.umrechnen(fremdBetrag, fremdWaehrungId);
    assertThat("Euro-Betrag", actual, comparesEqualTo(expected));
  }
}
