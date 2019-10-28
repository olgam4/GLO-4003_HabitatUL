package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculator;

public class DummyQuoteBasePriceCalculator implements QuoteBasePriceCalculator {
  @Override
  public Money computeQuoteBasePrice(QuoteForm quoteForm) {
    return MoneyGenerator.create();
  }
}
