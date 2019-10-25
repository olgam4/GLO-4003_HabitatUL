package ca.ulaval.glo4003.underwriting.domain.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteExpiredError extends QuoteError {
  private static final String ERROR = "QUOTE_EXPIRED";
  private static final String MESSAGE = "sorry, quote with id <%s> is expired";

  public QuoteExpiredError(QuoteId quoteId) {
    super(ERROR, String.format(MESSAGE, quoteId.toRepresentation()));
  }
}
