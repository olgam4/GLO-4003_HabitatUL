package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuoteBasicBlockBasePremiumCalculator
    extends BasePremiumCalculator<QuotePremiumInput> {
  Money compute(QuotePremiumInput quotePremiumInput);
}
