package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;

public class DummyQuoteBasicBlockBasePremiumCalculator
    implements QuoteBasicBlockBasePremiumCalculator {
  @Override
  public Money compute(QuotePremiumInput quotePremiumInput) {
    return createMoney();
  }
}
