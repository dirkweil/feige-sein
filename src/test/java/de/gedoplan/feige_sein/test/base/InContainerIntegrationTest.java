package de.gedoplan.feige_sein.test.base;

import de.gedoplan.feige_sein.test.data.MasterTestDataService;

import javax.inject.Inject;

import org.jboss.shrinkwrap.api.spec.WebArchive;

public class InContainerIntegrationTest extends IntegrationTest {
  protected static WebArchive createInContainerTestDeployment() {
    WebArchive archive = IntegrationTest.createBaseDeployment();

    archive.addClasses(IntegrationTest.class, InContainerIntegrationTest.class);

    return archive;
  }

  @Inject
  MasterTestDataService masterTestDataService;

  boolean initialized = false;

  protected void loadTestData(int level) {
    if (!this.initialized) {
      this.masterTestDataService.createTestFixture(level);
      this.initialized = true;
    }
  }

}
