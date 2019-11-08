package ca.ulaval.glo4003.calculator.infrastructure.premium;

import ca.ulaval.glo4003.calculator.domain.premium.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.math.BigDecimal;

public class HardCodedQuoteBasePremiumCalculator implements QuoteBasePremiumCalculator {
  static final Money HARDCODED_PREMIUM = new Money(new Amount(BigDecimal.valueOf(200)));

  @Override
  public Money computeQuoteBasePremium(QuotePremiumInput quotePremiumInput) {
    return HARDCODED_PREMIUM;
  }
}
