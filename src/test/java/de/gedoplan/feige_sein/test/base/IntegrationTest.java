package de.gedoplan.feige_sein.test.base;

import de.gedoplan.feige_sein.common.persistence.EntityManagerProducer;
import de.gedoplan.feige_sein.shop.persistence.Artikel;
import de.gedoplan.feige_sein.test.data.MasterTestDataService;
import de.gedoplan.feige_sein.test.util.ExceptionUtil;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.io.File;
import java.io.FilenameFilter;
import java.util.UUID;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Filter;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class IntegrationTest
{
  public static final String deploymentUnitName = UUID.randomUUID().toString();

  protected static WebArchive createBaseDeployment()
  {
    WebArchive archive = ShrinkWrap.create(WebArchive.class, deploymentUnitName + ".war");

    // Persistente Klassen und Repositories ins Archiv aufnehmen
    // TODO: Testklassen ausnehmen. Sollte eigentlich alle Klassen aus dem Test-Classpath aussortieren. Ging aber erstmal nur mit Pattern.
    Filter<ArchivePath> excludeTestClasses = Filters.exclude(".*Test\\.class");
    archive.addPackages(true, excludeTestClasses, EntityManagerProducer.class.getPackage());
    archive.addPackages(true, excludeTestClasses, Waehrung.class.getPackage());
    archive.addPackages(true, excludeTestClasses, Artikel.class.getPackage());

    // Unterstützende Klassen hinzufügen
    archive.addPackages(true, MasterTestDataService.class.getPackage());
    archive.addPackages(true, ExceptionUtil.class.getPackage());

    // Deskriptoren hinzufügen
    File[] webInfDescriptors = new File("src/main/webapp/WEB-INF").listFiles(new FilenameFilter()
    {
      @Override
      public boolean accept(File dir, String name)
      {
        return name.endsWith(".xml");
      }
    });
    for (File webInfDescriptor : webInfDescriptors)
    {
      archive.addAsWebInfResource(webInfDescriptor);
    }
    archive.addAsResource("META-INF/beans.xml");
    archive.addAsResource("integrationtest/persistence.xml", "META-INF/persistence.xml");

    // Benötige Bibiotheken hinzufügen
    File[] dependencies = Maven.resolver().offline().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
    archive.addAsLibraries(dependencies);

    return archive;
  }
}
