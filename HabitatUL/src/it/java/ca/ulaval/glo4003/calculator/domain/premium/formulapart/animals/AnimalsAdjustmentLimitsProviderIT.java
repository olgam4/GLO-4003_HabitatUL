package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentLimitsProviderIT {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();

  private AnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideMinimumAdjustment() {
    PremiumAdjustment adjustment = subject.getMinimumAdjustment(BASE_PREMIUM);

    assertNotNull(adjustment);
  }

  @Test
  public void gettingAdjustment_shouldProvideMaximumAdjustment() {
    PremiumAdjustment adjustment = subject.getMaximumAdjustment(BASE_PREMIUM);

    assertNotNull(adjustment);
  }

  public abstract AnimalsAdjustmentLimitsProvider createSubject();
}
