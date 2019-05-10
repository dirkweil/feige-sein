package de.gedoplan.feige_sein.shop.gui.inspect;

import de.gedoplan.feige_sein.shop.persistence.Bestellung;

import javax.ejb.Remote;

@Remote
public interface ShopGuiIntegrationTestInspectorRemote {
  public boolean checkBestellungExists(Bestellung bestellung);
}
