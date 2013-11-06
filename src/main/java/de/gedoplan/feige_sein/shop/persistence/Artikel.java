package de.gedoplan.feige_sein.shop.persistence;

import de.gedoplan.baselibs.persistence.entity.GeneratedLongIdEntity;
import de.gedoplan.baselibs.utils.constraint.NotEmpty;
import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
@Table(name = Artikel.TABLE_NAME)
public class Artikel extends GeneratedLongIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "FEIGE_SEIN_ARTIKEL";

  @NotNull
  @NotEmpty
  private String             name;

  @NotNull
  @DecimalMin("0")
  @Column(precision = 10, scale = 4)
  private BigDecimal         preis;

  @NotNull
  @ManyToOne
  private Waehrung           waehrung;

  public Artikel(String name, BigDecimal preis, Waehrung waehrung)
  {
    this.name = name;
    this.preis = preis;
    this.waehrung = waehrung;
  }

  protected Artikel()
  {
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public BigDecimal getPreis()
  {
    return this.preis;
  }

  public void setPreis(BigDecimal preis)
  {
    this.preis = preis;
  }

  public Waehrung getWaehrung()
  {
    return this.waehrung;
  }

  public void setWaehrung(Waehrung waehrung)
  {
    this.waehrung = waehrung;
  }
}
