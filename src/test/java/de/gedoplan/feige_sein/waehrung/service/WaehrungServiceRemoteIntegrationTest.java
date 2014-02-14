package de.gedoplan.feige_sein.waehrung.service;

import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.*;

import de.gedoplan.baselibs.utils.remote.ServiceLocator;
import de.gedoplan.feige_sein.test.base.RemoteIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.math.BigDecimal;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WaehrungServiceRemoteIntegrationTest extends RemoteIntegrationTest
{
  @Deployment(testable = false)
  public static WebArchive createDeployment()
  {
    WebArchive archive = RemoteIntegrationTest.createRemoteTestDeployment();

    // Zu testende Klassen im Archiv aufnehmen
    archive.addClasses(WaehrungService.class, WaehrungServiceRemote.class);

    //    System.out.println(archive.toString(true));

    return archive;
  }

  @BeforeClass
  public static void beforeClass()
  {
    loadTestData(TestLevel.WAEHRUNG.ordinal());
  }

  @Test
  public void testUmrechnenUSD()
  {
    BigDecimal fremdBetrag = new BigDecimal(10000);
    Waehrung fremdWaehrung = WaehrungTestDataService.WAEHRUNG_USD;
    String fremdWaehrungId = fremdWaehrung.getId();
    BigDecimal expected = fremdBetrag.multiply(fremdWaehrung.getEuroValue());

    WaehrungServiceRemote waehrungService = ServiceLocator.getEjb(WaehrungServiceRemote.class, null, deploymentUnitName, WaehrungService.class.getSimpleName());
    BigDecimal actual = waehrungService.umrechnen(fremdBetrag, fremdWaehrungId);

    assertThat("Euro-Betrag", actual, comparesEqualTo(expected));
  }
}
