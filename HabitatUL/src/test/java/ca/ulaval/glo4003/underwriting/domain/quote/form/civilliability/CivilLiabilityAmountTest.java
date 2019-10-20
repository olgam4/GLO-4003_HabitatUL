package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CivilLiabilityAmountTest {
  @Test
  public void
      gettingEnum_withKnownCivilLiabilityAmount_shouldReturnCorrespondingCivilLiabilityAmount()
          throws InvalidArgumentException {
    assertEquals(CivilLiabilityAmount.ONE_MILLION, CivilLiabilityAmount.getEnum("1M"));
    assertEquals(CivilLiabilityAmount.TWO_MILLION, CivilLiabilityAmount.getEnum("2M"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(CivilLiabilityAmount.ONE_MILLION, CivilLiabilityAmount.getEnum("1m"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownCivilLiabilityAmount_shouldThrow()
      throws InvalidArgumentException {
    CivilLiabilityAmount.getEnum("UNKNOWN_CIVIL_LIABILITY");
  }
}
