package de.gedoplan.feige_sein.waehrung.service;

import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.persistence.WaehrungRepository;
import de.gedoplan.feige_sein.waehrung.service.WaehrungService;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class WaehrungServiceUnitTest
{
  private static final Waehrung           USD                 = new Waehrung("USD", new BigDecimal("0.8341"));
  private static final WaehrungRepository WAEHRUNG_REPOSITORY = Mockito.mock(WaehrungRepository.class);

  private static final WaehrungService    WAEHRUNG_SERVICE    = new WaehrungService();

  @BeforeClass
  public static void beforeClass()
  {
    Mockito.when(WAEHRUNG_REPOSITORY.findById("USD")).thenReturn(USD);
    WAEHRUNG_SERVICE.waehrungRepository = WAEHRUNG_REPOSITORY;
  }

  @Test
  public void testUmrechnenUSD()
  {
    BigDecimal fremdBetrag = new BigDecimal(100);

    BigDecimal expected = fremdBetrag.multiply(USD.getEuroValue());
    BigDecimal actual = WAEHRUNG_SERVICE.umrechnen(fremdBetrag, USD.getId());
    Assert.assertEquals("Euro-Betrag", expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUmrechnenNUL()
  {
    BigDecimal fremdBetrag = new BigDecimal(100);
    WAEHRUNG_SERVICE.umrechnen(fremdBetrag, "NUL");
  }

}
