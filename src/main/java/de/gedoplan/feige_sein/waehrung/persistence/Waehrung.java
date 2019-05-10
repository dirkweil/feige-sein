package de.gedoplan.feige_sein.waehrung.persistence;

import de.gedoplan.baselibs.persistence.entity.StringIdEntity;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Access(AccessType.FIELD)
@Table(name = Waehrung.TABLE_NAME)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Waehrung extends StringIdEntity {
  private static final long serialVersionUID = 1L;

  public static final String TABLE_NAME = "FEIGE_SEIN_WAEHRUNG";

  @Column(precision = 10, scale = 4)
  private BigDecimal euroValue;

  public Waehrung(String id, BigDecimal euroValue) {
    this.id = id;
    this.euroValue = euroValue;
  }

  protected Waehrung() {
  }

  public BigDecimal getEuroValue() {
    return this.euroValue;
  }

  public void setEuroValue(BigDecimal euroValue) {
    this.euroValue = euroValue;
  }
}
