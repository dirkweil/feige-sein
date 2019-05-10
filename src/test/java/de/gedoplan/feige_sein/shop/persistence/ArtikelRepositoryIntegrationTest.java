package de.gedoplan.feige_sein.shop.persistence;

import de.gedoplan.feige_sein.test.base.InContainerIntegrationTest;
import de.gedoplan.feige_sein.test.data.ShopTestDataService;
import de.gedoplan.feige_sein.test.data.TestLevel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(Arquillian.class)
public class ArtikelRepositoryIntegrationTest extends InContainerIntegrationTest {
  @Deployment
  public static WebArchive createDeployment() {
    WebArchive archive = InContainerIntegrationTest.createInContainerTestDeployment();

    // TODO Redundanz zum POM. Sollte einfacher gehen.
    // Achtung: importRuntimeDependencies().resolve() enthält zu viel. Schön wäre, wenn man nur die Test-Dependencies des pom.xml
    // ohne sein Parent auflösen könnte-
    archive.addAsLibraries(Maven.resolver().offline().loadPomFromFile("pom.xml").resolve("org.unitils:unitils-core").withTransitivity().asFile());

    // System.out.println(archive.toString(true));

    return archive;
  }

  @Before
  public void before() {
    loadTestData(TestLevel.SHOP.ordinal());
  }

  @Inject
  ArtikelRepository artikelRepository;

  @Test
  public void testTestDataLoaded() {
    List<Artikel> expected = new ArrayList<>();
    expected.add(ShopTestDataService.ARTIKEL_FLUG_DUS_JFK);
    expected.add(ShopTestDataService.ARTIKEL_HOTEL_MARRIOTT_NYC);

    List<Artikel> actual = this.artikelRepository.findAll();

    ReflectionAssert.assertLenientEquals("Alle Artikel", expected, actual);
  }

}
