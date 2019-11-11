package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteBasePremiumCalculatorIT {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();

  private QuoteBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuoteBasePremium_shouldReturnBasePremium() {
    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    assertNotNull(premium);
  }

  public abstract QuoteBasePremiumCalculator createSubject();
}
