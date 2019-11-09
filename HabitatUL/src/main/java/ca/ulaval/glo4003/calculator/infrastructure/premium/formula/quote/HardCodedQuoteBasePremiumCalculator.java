package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.math.BigDecimal;

public class HardCodedQuoteBasePremiumCalculator implements QuoteBasePremiumCalculator {
  static final Money HARDCODED_PREMIUM = new Money(new Amount(BigDecimal.valueOf(200)));

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput) {
    return HARDCODED_PREMIUM;
  }
}
