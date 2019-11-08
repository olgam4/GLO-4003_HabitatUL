package ca.ulaval.glo4003.calculator.domain.premium;

import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuoteBasePremiumCalculator {
  Money computeQuoteBasePremium(QuotePremiumInput quotePremiumInput);
}
