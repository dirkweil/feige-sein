package de.gedoplan.feige_sein.test.base;

import de.gedoplan.baselibs.naming.JNDIContextFactory;
import de.gedoplan.baselibs.utils.remote.ServiceLocator;
import de.gedoplan.feige_sein.test.data.MasterTestDataService;
import de.gedoplan.feige_sein.test.data.MasterTestDataServiceRemote;
import de.gedoplan.feige_sein.test.util.TestProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class RemoteIntegrationTest extends IntegrationTest {
  public static final String serverUrlRemote = TestProperties.getProperty("server.url.remote", "localhost");
  public static final String serverUrlWeb = TestProperties.getProperty("server.url.web", "http://localhost:8080");
  public static final String serverUrlWebContext = serverUrlWeb + "/" + deploymentUnitName;

  private static Log log = LogFactory.getLog(RemoteIntegrationTest.class);

  static {
    if (log.isDebugEnabled()) {
      log.debug("serverUrlRemote: " + serverUrlRemote);
      log.debug("serverUrlWeb: " + serverUrlWeb);
      log.debug("serverUrlWebContext: " + serverUrlWebContext);
    }

    if (serverUrlRemote != null) {
      System.setProperty(JNDIContextFactory.SERVERURL_SYSPROP, serverUrlRemote);
    }
  }

  protected static WebArchive createRemoteTestDeployment() {
    WebArchive archive = IntegrationTest.createBaseDeployment();
    return archive;
  }

  protected static void loadTestData(int level) {
    MasterTestDataServiceRemote masterTestDataService = ServiceLocator.getEjb(MasterTestDataServiceRemote.class, null, deploymentUnitName, MasterTestDataService.class.getSimpleName());
    masterTestDataService.createTestFixture(level);
  }

}
