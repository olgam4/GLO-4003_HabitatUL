package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.calculator.application.premium.PremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.QuotePremiumFormula;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PremiumCalculatorTest {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();
  private static final Money PREMIUM = MoneyGenerator.createMoney();

  @Mock private QuotePremiumFormula quotePremiumFormula;

  private PremiumCalculator subject;

  @Before
  public void setUp() {
    when(quotePremiumFormula.compute(any(QuotePremiumInput.class))).thenReturn(PREMIUM);
    subject = new PremiumCalculator(quotePremiumFormula);
  }

  @Test
  public void computingQuotePremium_shouldComputeQuotePremiumFormula() {
    subject.computeQuotePremium(QUOTE_PREMIUM_INPUT);

    verify(quotePremiumFormula).compute(QUOTE_PREMIUM_INPUT);
  }

  @Test
  public void computingQuotePremium_shouldReturnComputedQuotePremium() {
    Money quotePremium = subject.computeQuotePremium(QUOTE_PREMIUM_INPUT);

    assertEquals(PREMIUM, quotePremium);
  }
}
