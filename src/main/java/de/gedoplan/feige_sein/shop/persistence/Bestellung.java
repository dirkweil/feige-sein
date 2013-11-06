package de.gedoplan.feige_sein.shop.persistence;

import de.gedoplan.baselibs.persistence.entity.GeneratedLongIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
@Table(name = Bestellung.TABLE_NAME)
public class Bestellung extends GeneratedLongIdEntity
{
  private static final long     serialVersionUID = 1L;

  public static final String    TABLE_NAME       = "FEIGE_SEIN_BESTELLUNG";

  @NotNull
  @NotEmpty
  private String                besteller;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "BESTELLUNG_ID")
  @OrderColumn
  private List<Bestellposition> bestellpositionen;

  public Bestellung(String besteller)
  {
    this.besteller = besteller;
    this.bestellpositionen = new ArrayList<>();
  }

  protected Bestellung()
  {
  }

  public String getBesteller()
  {
    return this.besteller;
  }

  public void setBesteller(String besteller)
  {
    this.besteller = besteller;
  }

  public List<Bestellposition> getBestellpositionen()
  {
    return this.bestellpositionen;
  }

}
