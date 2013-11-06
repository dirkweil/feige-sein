package de.gedoplan.feige_sein.shop.gui;

import de.gedoplan.baselibs.utils.remote.ServiceLocator;
import de.gedoplan.feige_sein.shop.faces.converter.ArtikelConverter;
import de.gedoplan.feige_sein.shop.gui.inspect.ShopGuiIntegrationTestInspector;
import de.gedoplan.feige_sein.shop.gui.inspect.ShopGuiIntegrationTestInspectorRemote;
import de.gedoplan.feige_sein.shop.model.ShopModel;
import de.gedoplan.feige_sein.shop.persistence.Artikel;
import de.gedoplan.feige_sein.shop.persistence.Bestellposition;
import de.gedoplan.feige_sein.shop.persistence.Bestellung;
import de.gedoplan.feige_sein.test.base.RemoteIntegrationTest;
import de.gedoplan.feige_sein.test.data.TestLevel;
import de.gedoplan.feige_sein.test.util.TestProperties;

import java.io.File;
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
import org.openqa.selenium.support.ui.Select;

@RunAsClient
@RunWith(Arquillian.class)
public class ShopGuiIntegrationTest extends RemoteIntegrationTest
{
  @Deployment(testable = false)
  public static WebArchive createDeployment()
  {
    WebArchive archive = RemoteIntegrationTest.createRemoteTestDeployment();

    // Zu testende Klassen im Archiv aufnehmen
    archive.addClasses(ShopModel.class, ArtikelConverter.class);

    archive.addAsWebResource(new File("src/main/webapp/demo/shop.xhtml"), "demo/shop.xhtml");
    archive.addAsWebResource(new File("src/main/webapp/demo/bestellung.xhtml"), "demo/bestellung.xhtml");

    // Inspektoren aufnehmen
    archive.addClasses(ShopGuiIntegrationTestInspector.class, ShopGuiIntegrationTestInspectorRemote.class);

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
    loadTestData(TestLevel.SHOP.ordinal());
  }

  @Before
  public void before()
  {
    this.webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreateBestellung()
  {
    Bestellung expected = new Bestellung("Mustermann");
    Bestellposition bestellposition0 = new Bestellposition(2, new Artikel("Flug DUS-JFK", null, null));
    expected.getBestellpositionen().add(bestellposition0);
    Bestellposition bestellposition1 = new Bestellposition(1, new Artikel("Marriott Marquis", null, null));
    expected.getBestellpositionen().add(bestellposition1);

    this.webDriver.get(serverUrlWebContext + "/demo/shop.xhtml");

    this.webDriver.findElement(By.id("form:bestellungen:create")).click();

    WebElement bestellerField = this.webDriver.findElement(By.id("form:besteller"));
    bestellerField.clear();
    bestellerField.sendKeys(expected.getBesteller());

    this.webDriver.findElement(By.id("form:addPos")).click();

    WebElement anzahlField = this.webDriver.findElement(By.id("form:bestellpositionen:0:anzahl"));
    anzahlField.clear();
    anzahlField.sendKeys(Integer.toString(bestellposition0.getAnzahl()));

    new Select(this.webDriver.findElement(By.id("form:bestellpositionen:0:artikel"))).selectByVisibleText(bestellposition0.getArtikel().getName());

    this.webDriver.findElement(By.id("form:addPos")).click();

    anzahlField = this.webDriver.findElement(By.id("form:bestellpositionen:1:anzahl"));
    anzahlField.clear();
    anzahlField.sendKeys(Integer.toString(bestellposition1.getAnzahl()));

    new Select(this.webDriver.findElement(By.id("form:bestellpositionen:1:artikel"))).selectByVisibleText(bestellposition1.getArtikel().getName());

    ShopGuiIntegrationTest.this.webDriver.findElement(By.id("form:save")).click();

    ShopGuiIntegrationTestInspectorRemote inspector = ServiceLocator.getEjb(
        ShopGuiIntegrationTestInspectorRemote.class, null, deploymentUnitName, ShopGuiIntegrationTestInspector.class.getSimpleName());
    Assert.assertTrue("Bestellung nicht gespeichert", inspector.checkBestellungExists(expected));
  }

}
