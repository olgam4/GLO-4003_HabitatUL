package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MaximumPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MinimumPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentLimitsProvider.MAXIMUM_ADJUSTMENT_FACTOR;
import static ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentLimitsProvider.MINIMUM_ADJUSTMENT_FACTOR;
import static org.junit.Assert.assertEquals;

public class HardCodedAnimalsAdjustmentLimitsProviderTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();

  private HardCodedAnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentLimitsProvider();
  }

  @Test
  public void gettingMinimumAdjustment_shouldProvideCorrespondingAdjustment() {
    PremiumAdjustment minimumAdjustment = subject.getMinimumAdjustment(BASE_PREMIUM);

    MinimumPremiumAdjustment expectedAdjustment =
        new MinimumPremiumAdjustment(BASE_PREMIUM.multiply(MINIMUM_ADJUSTMENT_FACTOR));
    assertEquals(expectedAdjustment, minimumAdjustment);
  }

  @Test
  public void gettingMaximumAdjustment_shouldProvideCorrespondingAdjustment() {
    PremiumAdjustment maximumAdjustment = subject.getMaximumAdjustment(BASE_PREMIUM);

    MaximumPremiumAdjustment expectedAdjustment =
        new MaximumPremiumAdjustment(BASE_PREMIUM.multiply(MAXIMUM_ADJUSTMENT_FACTOR));
    assertEquals(expectedAdjustment, maximumAdjustment);
  }
}
