package de.gedoplan.feige_sein.waehrung.service;

import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface WaehrungServiceRemote {
  public BigDecimal getTauschkurs(String waehrungId);

  public BigDecimal umrechnen(BigDecimal betrag, String waehrungId);

}
