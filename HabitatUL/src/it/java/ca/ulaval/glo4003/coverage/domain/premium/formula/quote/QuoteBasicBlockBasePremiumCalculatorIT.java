package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteBasicBlockBasePremiumCalculatorIT {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();

  private QuoteBasicBlockBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuoteBasePremium_shouldReturnBasePremium() {
    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    assertNotNull(premium);
  }

  public abstract QuoteBasicBlockBasePremiumCalculator createSubject();
}