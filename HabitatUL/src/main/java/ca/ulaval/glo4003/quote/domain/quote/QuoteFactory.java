package ca.ulaval.glo4003.quote.domain.quote;

import ca.ulaval.glo4003.quote.domain.commons.Premium;

public class QuoteFactory {
  public Quote create(Premium premium, QuoteRequest quoteRequest) {
    return new Quote();
  }
}
