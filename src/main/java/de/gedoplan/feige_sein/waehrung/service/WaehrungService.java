package de.gedoplan.feige_sein.waehrung.service;

import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.persistence.WaehrungRepository;

import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class WaehrungService implements WaehrungServiceRemote {
  @Inject
  WaehrungRepository waehrungRepository;

  @Override
  public BigDecimal getTauschkurs(String waehrungId) {
    Waehrung waehrung = this.waehrungRepository.findById(waehrungId);
    if (waehrung != null) {
      return waehrung.getEuroValue();
    }

    throw new IllegalArgumentException("Waehrung " + waehrungId + " unbekannt");
  }

  @Override
  public BigDecimal umrechnen(BigDecimal betrag, String waehrungId) {
    return betrag.multiply(getTauschkurs(waehrungId));
  }

}
