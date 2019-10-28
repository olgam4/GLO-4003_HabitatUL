package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculator;

import java.math.BigDecimal;

public class HardCodedQuoteBasePriceCalculator implements QuoteBasePriceCalculator {
  static final Money PRICE = new Money(new Amount(BigDecimal.valueOf(200)));

  public Money computeQuoteBasePrice(QuoteForm quoteForm) {
    return PRICE;
  }
}
