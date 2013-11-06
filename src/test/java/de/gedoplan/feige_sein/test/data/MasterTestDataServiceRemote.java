package de.gedoplan.feige_sein.test.data;

import javax.ejb.Remote;

@Remote
public interface MasterTestDataServiceRemote
{
  /**
   * Laden (Erzeugen) von Testdaten.
   * 
   * @param maxLevel maximales Level
   */
  public void loadAllTestData(int maxLevel);

  /**
   * Testdaten komplett löschen.
   */
  public void unloadAllTestData();

  /**
   * Testfixture erstellen.
   * 
   * Dies ist die Kombination aus {@link #unloadAllTestData()} und {@link #loadAllTestData(int)}.
   * 
   * @param maxLevel maximales Level
   */
  public void createTestFixture(int maxLevel);
}
