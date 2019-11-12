package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyQuoteBasePremiumCalculator implements QuoteBasePremiumCalculator {
  @Override
  public Money compute(QuotePremiumInput quotePremiumInput) {
    return MoneyGenerator.createMoney();
  }
}
