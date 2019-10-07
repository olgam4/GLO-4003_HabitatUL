package ca.ulaval.glo4003.underwriting.domain.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface QuotePriceCalculator {
  Price computeQuotePrice(QuoteForm quoteForm);
}
