package de.gedoplan.feige_sein.waehrung.service;

import de.gedoplan.feige_sein.test.base.InContainerIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.service.WaehrungService;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WaehrungServiceGroupTest extends InContainerIntegrationTest
{
  protected static CdiContainer cdiContainer;

  @BeforeClass
  public static void beforeClass()
  {
    // JUL in slf4j umleiten (und damit am Ende in log4j). Achtung: Dies ist für Produktion zu inperformant!
    //    SLF4JBridgeHandler.removeHandlersForRootLogger();
    //    SLF4JBridgeHandler.install();

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
  //  @Ignore("CDI Container startet in GlassFish-Profilen nicht")
  public void testUmrechnenUSD()
  {
    BigDecimal fremdBetrag = new BigDecimal(10000);
    Waehrung fremdWaehrung = WaehrungTestDataService.WAEHRUNG_USD;
    String fremdWaehrungId = fremdWaehrung.getId();
    BigDecimal expected = fremdBetrag.multiply(fremdWaehrung.getEuroValue());

    BigDecimal actual = this.waehrungService.umrechnen(fremdBetrag, fremdWaehrungId);
    Assert.assertEquals("Euro-Betrag", expected, actual);
  }
}
