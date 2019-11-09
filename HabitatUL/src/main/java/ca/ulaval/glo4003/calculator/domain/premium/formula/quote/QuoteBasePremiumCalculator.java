package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuoteBasePremiumCalculator extends BasePremiumCalculator<QuotePremiumInput> {
  Money compute(QuotePremiumInput quotePremiumInput);
}
