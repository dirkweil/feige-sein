package de.gedoplan.feige_sein.test.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Master-Testdaten-Service.
 * 
 * Dies ist die Zusammenführung aller TestDataServices: Zum Löschen der Daten werden die entsprechenden Methoden aller
 * Teilservices in absteigender Level-Reihenfolge aufgerufen. Das Laden geschieht umgekehrt in aufsteigender Reihenfolge.
 * 
 * @author dw
 */
@Singleton
@LocalBean
public class MasterTestDataService implements MasterTestDataServiceRemote, Serializable
{
  private static final long                        serialVersionUID             = 1L;

  private static final Log                         LOGGER                       = LogFactory.getLog(MasterTestDataService.class);

  private static final Comparator<TestDataService> TEST_DATA_SERVICE_COMPARATOR = new Comparator<TestDataService>()
                                                                                {
                                                                                  @Override
                                                                                  public int compare(TestDataService s1, TestDataService s2)
                                                                                  {
                                                                                    return s1.getLevel() - s2.getLevel();
                                                                                  }
                                                                                };

  @Inject
  @Any
  private Instance<TestDataService>                testDataServices;

  private NavigableSet<TestDataService>            orderedTestDataServices;

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.feige_sein.test.data.MasterTestDataServiceRemote#loadAllTestData(int)
   */
  @Override
  public void loadAllTestData(int maxLevel)
  {
    try
    {
      for (TestDataService tds : this.orderedTestDataServices)
      {
        if (tds.getLevel() <= maxLevel)
        {
          tds.loadTestData();
        }
      }
    }
    catch (Exception ex) // CHECKSTYLE:IGNORE Catch von Exception ausnahmsweise erlaubt, da nur zum Logging verwendet
    {
      LOGGER.error("Kann Testdaten nicht laden", ex);
      throwAsRuntimeException(ex);
    }

  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.feige_sein.test.data.MasterTestDataServiceRemote#unloadAllTestData()
   */
  @Override
  public void unloadAllTestData()
  {
    try
    {
      for (TestDataService tds : this.orderedTestDataServices.descendingSet())
      {
        tds.unloadTestData();
      }
    }
    catch (Exception ex) // CHECKSTYLE:IGNORE Catch von Exception ausnahmsweise erlaubt, da nur zum Logging und Retyping verwendet
    {
      LOGGER.error("Kann Testdaten nicht entladen", ex);
      throwAsRuntimeException(ex);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.feige_sein.test.data.MasterTestDataServiceRemote#createTestFixture(int)
   */
  @Override
  public void createTestFixture(int maxLevel)
  {
    unloadAllTestData();
    loadAllTestData(maxLevel);
  }

  /**
   * Testdatenservices nach ihrem Level sortiert bereitstellen.
   */
  @PostConstruct
  private void sortTestDataServices()
  {
    this.orderedTestDataServices = new TreeSet<TestDataService>(TEST_DATA_SERVICE_COMPARATOR);
    for (TestDataService testDataService : this.testDataServices)
    {
      this.orderedTestDataServices.add(testDataService);
    }

    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("TestDataServices:");
      for (TestDataService testDataService : this.orderedTestDataServices)
      {
        LOGGER.debug(String.format("  %2d %s", testDataService.getLevel(), testDataService));
      }
    }
  }

  /**
   * Throwable als RuntimeException auswerfen.
   * 
   * Ist t eine RuntimeException, wird t unverändert ausgeworfen, ansonsten verpackt in eine neue RuntimeException.
   * 
   * @param t Throwable
   */
  private static void throwAsRuntimeException(Throwable t)
  {
    if (t instanceof RuntimeException)
    {
      throw (RuntimeException) t;
    }

    throw new RuntimeException(t);
  }

}
