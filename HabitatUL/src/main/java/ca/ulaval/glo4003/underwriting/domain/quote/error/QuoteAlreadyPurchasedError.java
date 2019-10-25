package ca.ulaval.glo4003.underwriting.domain.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPurchasedError extends QuoteError {
  private static final String ERROR = "QUOTE_ALREADY_PURCHASED";
  private static final String MESSAGE = "sorry, quote with id <%s> has already been purchased";

  public QuoteAlreadyPurchasedError(QuoteId quoteId) {
    super(ERROR, String.format(MESSAGE, quoteId.toRepresentation()));
  }
}
