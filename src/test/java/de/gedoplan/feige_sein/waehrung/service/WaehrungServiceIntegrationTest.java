package de.gedoplan.feige_sein.waehrung.service;

import de.gedoplan.feige_sein.test.base.InContainerIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WaehrungServiceIntegrationTest extends InContainerIntegrationTest
{
  @Deployment
  public static WebArchive createDeployment()
  {
    WebArchive archive = InContainerIntegrationTest.createInContainerTestDeployment();

    // Zu testende Klassen im Archiv aufnehmen
    archive.addClasses(WaehrungService.class, WaehrungServiceRemote.class);

    //    System.out.println(archive.toString(true));

    return archive;
  }

  @Before
  public void before()
  {
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
    Assert.assertEquals("Euro-Betrag", expected, actual);
  }
}
