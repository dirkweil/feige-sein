package de.gedoplan.feige_sein.waehrung.gui;

import de.gedoplan.feige_sein.test.base.RemoteIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.data.WaehrungTestDataService;
import de.gedoplan.feige_sein.test.util.TestProperties;
import de.gedoplan.feige_sein.waehrung.model.WaehrungModel;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.service.WaehrungService;
import de.gedoplan.feige_sein.waehrung.service.WaehrungServiceRemote;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RunWith(Arquillian.class)
public class WaehrungGuiIntegrationTest extends RemoteIntegrationTest
{
  @Deployment(testable = false)
  public static WebArchive createDeployment()
  {
    WebArchive archive = RemoteIntegrationTest.createRemoteTestDeployment();

    // Zu testende Klassen im Archiv aufnehmen
    archive.addClasses(WaehrungService.class, WaehrungServiceRemote.class, WaehrungModel.class);

    archive.addAsWebResource(new File("src/main/webapp/demo/waehrungsrechner.xhtml"), "demo/waehrungsrechner.xhtml");
    archive.addAsWebResource(new File("src/main/webapp/index.html"), "index.html");

    //    System.out.println(archive.toString(true));

    return archive;
  }

  private static String serverUrlWeb        = TestProperties.getProperty("server.url.web", "http://localhost:8080");
  private static String serverUrlWebContext = serverUrlWeb + "/" + deploymentUnitName;

  @Drone
  WebDriver             webDriver;

  @BeforeClass
  public static void beforeClass()
  {
    loadTestData(TestLevel.WAEHRUNG.ordinal());
  }

  @Before
  public void before()
  {
    this.webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  @RunAsClient
  public void testUmrechnenUSD()
  {
    BigDecimal fremdBetrag = new BigDecimal(10000);
    Waehrung fremdWaehrung = WaehrungTestDataService.WAEHRUNG_USD;
    String fremdWaehrungId = fremdWaehrung.getId();
    BigDecimal euroBetrag = fremdBetrag.multiply(fremdWaehrung.getEuroValue());
    NumberFormat euroFormatter = new DecimalFormat("#,##0.00");
    String expected = euroFormatter.format(euroBetrag);

    this.webDriver.get(serverUrlWebContext + "/demo/waehrungsrechner.xhtml");

    WebElement fremdBetragField = this.webDriver.findElement(By.id("form:fremdBetrag"));
    fremdBetragField.clear();
    fremdBetragField.sendKeys(fremdBetrag.toString());

    WebElement waehrungIdField = this.webDriver.findElement(By.id("form:waehrungId"));
    waehrungIdField.clear();
    waehrungIdField.sendKeys(fremdWaehrungId);

    WebElement umrechnenButton = this.webDriver.findElement(By.id("form:umrechnen"));
    umrechnenButton.click();

    WebElement euroBetragField = this.webDriver.findElement(By.id("form:euroBetrag"));
    String actual = euroBetragField.getText();

    Assert.assertEquals("Eurowert", expected, actual);
  }

}
