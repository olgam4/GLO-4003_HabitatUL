package ca.ulaval.glo4003.calculator.domain.premium.formula.input;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CivilLiabilityLimitTest {
  @Test
  public void
      gettingEnum_withKnownCivilLiabilityLimit_shouldReturnCorrespondingCivilLiabilityLimit()
          throws InvalidArgumentException {
    assertEquals(CivilLiabilityLimit.ONE_MILLION, CivilLiabilityLimit.getEnum("1M"));
    assertEquals(CivilLiabilityLimit.TWO_MILLION, CivilLiabilityLimit.getEnum("2M"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(CivilLiabilityLimit.ONE_MILLION, CivilLiabilityLimit.getEnum("1m"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownCivilLiabilityLimit_shouldThrow()
      throws InvalidArgumentException {
    CivilLiabilityLimit.getEnum("UNKNOWN_CIVIL_LIABILITY");
  }
}
