package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createQuotePremiumInput;
import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote.HardCodedQuoteBasicBlockBasePremiumCalculator.HARDCODED_PREMIUM;
import static org.junit.Assert.assertEquals;

public class HardCodedQuoteBasicBlockBasePremiumCalculatorTest {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = createQuotePremiumInput();

  private HardCodedQuoteBasicBlockBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedQuoteBasicBlockBasePremiumCalculator();
  }

  @Test
  public void computingQuoteBasePremium_shouldReturnHardCodedPremium() {
    Money premium = subject.compute(QUOTE_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
