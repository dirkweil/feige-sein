package de.gedoplan.feige_sein.shop.gui.inspect;

import de.gedoplan.feige_sein.shop.persistence.Bestellung;
import de.gedoplan.feige_sein.shop.persistence.BestellungRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ShopGuiIntegrationTestInspector implements ShopGuiIntegrationTestInspectorRemote {
  @Inject
  BestellungRepository bestellungRepository;

  @Override
  public boolean checkBestellungExists(Bestellung expected) {
    List<Bestellung> bestellungen = this.bestellungRepository.findAll();
    for (Bestellung bestellung : bestellungen) {
      if (bestellung.getBesteller().equals(expected.getBesteller())) {
        // TODO: Hier wird nur auf korrekten Besteller getestet. Im Endausbau müsste die gesamte Bestellung geprüft werden!
        return true;
      }
    }

    return false;
  }

}
