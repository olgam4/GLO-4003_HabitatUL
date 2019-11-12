package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote.HardCodedQuoteBasePremiumCalculator.HARDCODED_PREMIUM;
import static org.junit.Assert.assertEquals;

public class HardCodedQuoteBasePremiumCalculatorTest {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();

  private HardCodedQuoteBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedQuoteBasePremiumCalculator();
  }

  @Test
  public void computingQuoteBasePremium_shouldReturnHardCodedPremium() {
    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
