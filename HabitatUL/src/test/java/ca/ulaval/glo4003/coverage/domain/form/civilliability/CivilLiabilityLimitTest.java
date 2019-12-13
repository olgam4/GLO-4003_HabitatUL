package ca.ulaval.glo4003.coverage.domain.form.civilliability;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Test;

import java.math.BigDecimal;

import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmount;
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

  @Test
  public void convertingAmountIntoCivilLiabilityLimit_shouldReturnCorrespondingCivilLiabilityLimit()
      throws InvalidArgumentException {
    assertEquals(
        CivilLiabilityLimit.ONE_MILLION,
        CivilLiabilityLimit.fromAmount(new Amount(BigDecimal.valueOf(1000000))));
    assertEquals(
        CivilLiabilityLimit.TWO_MILLION,
        CivilLiabilityLimit.fromAmount(new Amount(BigDecimal.valueOf(2000000))));
  }

  @Test(expected = InvalidArgumentException.class)
  public void convertingAmountIntoCivilLiabilityLimit_withUnknownCivilLiabilityLimit_shouldThrow()
      throws InvalidArgumentException {
    CivilLiabilityLimit.fromAmount(createAmount());
  }
}
