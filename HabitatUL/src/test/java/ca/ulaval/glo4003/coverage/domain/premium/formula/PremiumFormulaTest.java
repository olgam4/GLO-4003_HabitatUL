package ca.ulaval.glo4003.coverage.domain.premium.formula;

import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class PremiumFormulaTest<T> {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money ANOTHER_PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();

  @Mock private BasePremiumCalculator basePremiumCalculator;
  @Mock private PremiumFormulaPart premiumFormulaPart;
  @Mock private PremiumFormulaPart anotherPremiumFormulaPart;

  private PremiumFormula subject;
  private T input;

  @Before
  public void setUp() {
    input = createInput();
    when(basePremiumCalculator.compute(eq(input))).thenReturn(BASE_PREMIUM);
    when(premiumFormulaPart.compute(eq(input), any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    when(anotherPremiumFormulaPart.compute(eq(input), any(Money.class)))
        .thenReturn(ANOTHER_PREMIUM_ADJUSTMENT);
    subject = new PremiumFormula(basePremiumCalculator);
  }

  @Test
  public void computingPremium_withoutAdditionalParts_shouldReturnBasePremium() {
    Money premium = subject.compute(input);

    assertEquals(BASE_PREMIUM, premium);
  }

  @Test
  public void computingPremium_withAdditionalParts_shouldConsiderAllFormulaParts() {
    subject.addFormulaPart(premiumFormulaPart);
    subject.addFormulaPart(anotherPremiumFormulaPart);

    Money premium = subject.compute(input);

    Money expectedPremium = BASE_PREMIUM.add(PREMIUM_ADJUSTMENT).add(ANOTHER_PREMIUM_ADJUSTMENT);
    assertEquals(expectedPremium, premium);
  }

  public abstract T createInput();
}
