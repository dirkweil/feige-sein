package de.gedoplan.feige_sein.waehrung.webservice;

import de.gedoplan.feige_sein.common.webservice.RestApplication;
import de.gedoplan.feige_sein.test.base.RemoteIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.service.WaehrungService;
import de.gedoplan.feige_sein.waehrung.service.WaehrungServiceRemote;
import de.gedoplan.feige_sein.waehrung.webservice.WaehrungRestService;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WaehrungRestServiceIntegrationTest extends RemoteIntegrationTest
{
  @Deployment(testable = false)
  public static WebArchive createDeployment()
  {
    WebArchive archive = RemoteIntegrationTest.createRemoteTestDeployment();

    // Zu testende Klassen im Archiv aufnehmen
    archive.addClasses(WaehrungService.class, WaehrungServiceRemote.class);
    archive.addClasses(RestApplication.class);
    archive.addClasses(WaehrungRestService.class);

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

    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(serverUrlWebContext + "/rs/waehrung/" + fremdWaehrungId + "/" + fremdBetrag);
    BigDecimal actual = target.request(MediaType.TEXT_PLAIN).get(BigDecimal.class);

    Assert.assertEquals("Euro-Betrag", expected, actual);
  }
}
