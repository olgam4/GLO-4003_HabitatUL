package ca.ulaval.glo4003.underwriting.application.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotFoundError extends QuoteAppServiceError {
  private static final String ERROR = "QUOTE_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find quote with id <%s>";

  public QuoteNotFoundError(QuoteId quoteId) {
    super(ERROR, String.format(MESSAGE, quoteId.toRepresentation()));
  }
}
