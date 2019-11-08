package ca.ulaval.glo4003.calculator.infrastructure.premium;

import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.calculator.infrastructure.premium.HardCodedQuoteBasePremiumCalculator.HARDCODED_PREMIUM;
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
    Money premium = subject.computeQuoteBasePremium(QUOTE_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
