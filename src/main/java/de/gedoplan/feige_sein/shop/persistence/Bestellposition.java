package de.gedoplan.feige_sein.shop.persistence;

import de.gedoplan.baselibs.persistence.entity.GeneratedLongIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
@Table(name = Bestellposition.TABLE_NAME)
public class Bestellposition extends GeneratedLongIdEntity
{
  private static final long  serialVersionUID = 1L;

  public static final String TABLE_NAME       = "FEIGE_SEIN_BESTELLPOS";

  @Min(1)
  private int                anzahl;

  @NotNull
  @ManyToOne
  private Artikel            artikel;

  public Bestellposition(int anzahl, Artikel artikel)
  {
    this.anzahl = anzahl;
    this.artikel = artikel;
  }

  protected Bestellposition()
  {
  }

  public int getAnzahl()
  {
    return this.anzahl;
  }

  public void setAnzahl(int anzahl)
  {
    this.anzahl = anzahl;
  }

  public Artikel getArtikel()
  {
    return this.artikel;
  }

  public void setArtikel(Artikel artikel)
  {
    this.artikel = artikel;
  }

}
