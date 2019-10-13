package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface QuoteIndicatedPriceCalculator {
  Money computeIndicatedQuotePrice(QuoteForm quoteForm);
}
