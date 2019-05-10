package de.gedoplan.feige_sein.shop.model;

import de.gedoplan.feige_sein.shop.persistence.Artikel;
import de.gedoplan.feige_sein.shop.persistence.ArtikelRepository;
import de.gedoplan.feige_sein.shop.persistence.Bestellposition;
import de.gedoplan.feige_sein.shop.persistence.Bestellung;
import de.gedoplan.feige_sein.shop.persistence.BestellungRepository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@ConversationScoped
public class ShopModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Inject
  Conversation conversation;

  @Inject
  ArtikelRepository artikelRepository;

  private List<Artikel> artikelList;

  @Inject
  BestellungRepository bestellungRepository;

  private List<Bestellung> bestellungen;

  @PostConstruct
  private void postConstruct() {
    this.artikelList = this.artikelRepository.findAll();
    this.bestellungen = this.bestellungRepository.findAll();
  }

  public List<Artikel> getArtikelList() {
    return this.artikelList;
  }

  public List<Bestellung> getBestellungen() {
    return this.bestellungen;
  }

  private Bestellung bestellung;

  public Bestellung getBestellung() {
    return this.bestellung;
  }

  public String editBestellung(Bestellung bestellung) {
    this.conversation.begin();
    this.bestellung = bestellung;
    return "edit";
  }

  public String createBestellung() {
    this.conversation.begin();
    this.bestellung = new Bestellung(null);
    return "edit";
  }

  public String saveBestellung() {
    this.bestellungRepository.merge(this.bestellung);
    this.conversation.end();
    return "ok";
  }

  public String cancelBestellung() {
    this.conversation.end();
    return "ok";
  }

  public void addBestellposition() {
    this.bestellung.getBestellpositionen().add(new Bestellposition(0, null));
  }
}
