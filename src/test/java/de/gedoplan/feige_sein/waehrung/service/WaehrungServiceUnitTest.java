package de.gedoplan.feige_sein.waehrung.service;

import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.*;

import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.persistence.WaehrungRepository;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class WaehrungServiceUnitTest {
  private static final Waehrung USD = new Waehrung("USD", new BigDecimal("0.8341"));
  private static WaehrungRepository waehrungRepository;
  private static WaehrungService waehrungService;

  @BeforeClass
  public static void beforeClass() {
    waehrungRepository = Mockito.mock(WaehrungRepository.class);
    Mockito.when(waehrungRepository.findById("USD")).thenReturn(USD);

    waehrungService = new WaehrungService();
    waehrungService.waehrungRepository = waehrungRepository;
  }

  @Test
  public void testUmrechnenUSD() {
    BigDecimal fremdBetrag = new BigDecimal(100);

    BigDecimal expected = fremdBetrag.multiply(USD.getEuroValue());
    BigDecimal actual = waehrungService.umrechnen(fremdBetrag, USD.getId());

    assertThat("Euro-Betrag", actual, comparesEqualTo(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUmrechnenNUL() {
    BigDecimal fremdBetrag = new BigDecimal(100);
    waehrungService.umrechnen(fremdBetrag, "NUL");
  }

}
