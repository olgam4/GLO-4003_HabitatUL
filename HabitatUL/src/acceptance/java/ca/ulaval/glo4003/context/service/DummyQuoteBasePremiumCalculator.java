package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyQuoteBasePremiumCalculator implements QuoteBasePremiumCalculator {
  @Override
  public Money computeQuoteBasePremium(QuotePremiumInput quotePremiumInput) {
    return MoneyGenerator.createMoney();
  }
}
