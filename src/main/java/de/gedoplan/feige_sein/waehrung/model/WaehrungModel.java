package de.gedoplan.feige_sein.waehrung.model;

import de.gedoplan.feige_sein.waehrung.service.WaehrungService;

import java.math.BigDecimal;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class WaehrungModel {
  private BigDecimal fremdBetrag;
  private String waehrungId;
  private BigDecimal euroBetrag;

  @Inject
  WaehrungService waehrungService;

  public BigDecimal getFremdBetrag() {
    return this.fremdBetrag;
  }

  public void setFremdBetrag(BigDecimal fremdBetrag) {
    this.fremdBetrag = fremdBetrag;
  }

  public String getWaehrungId() {
    return this.waehrungId;
  }

  public void setWaehrungId(String waehrungId) {
    this.waehrungId = waehrungId;
  }

  public BigDecimal getEuroBetrag() {
    return this.euroBetrag;
  }

  public void umrechnen() {
    this.euroBetrag = this.waehrungService.umrechnen(this.fremdBetrag, this.waehrungId);
  }

}
