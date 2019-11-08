package ca.ulaval.glo4003.calculator.domain.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuotePremiumFormulaTest {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money ANOTHER_PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();

  @Mock private QuoteBasePremiumCalculator quoteBasePremiumCalculator;
  @Mock private QuotePremiumFormulaPart formulaPart;
  @Mock private QuotePremiumFormulaPart anotherFormulaPart;

  private QuotePremiumFormula subject;

  @Before
  public void setUp() {
    when(quoteBasePremiumCalculator.computeQuoteBasePremium(any(QuotePremiumInput.class)))
        .thenReturn(BASE_PREMIUM);
    when(formulaPart.compute(any(QuotePremiumInput.class), any(Money.class)))
        .thenReturn(PREMIUM_ADJUSTMENT);
    when(anotherFormulaPart.compute(any(QuotePremiumInput.class), any(Money.class)))
        .thenReturn(ANOTHER_PREMIUM_ADJUSTMENT);
    subject = new QuotePremiumFormula(quoteBasePremiumCalculator);
  }

  @Test
  public void computingPremium_withoutAdjustments_shouldReturnBasePremium() {
    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    assertEquals(BASE_PREMIUM, premium);
  }

  @Test
  public void computingPremium_withAdjustments_shouldReturnAdjustedPremium() {
    subject.addFormulaPart(formulaPart);
    subject.addFormulaPart(anotherFormulaPart);

    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    Money expectedPremium = BASE_PREMIUM.add(PREMIUM_ADJUSTMENT).add(ANOTHER_PREMIUM_ADJUSTMENT);
    assertEquals(expectedPremium, premium);
  }
}
