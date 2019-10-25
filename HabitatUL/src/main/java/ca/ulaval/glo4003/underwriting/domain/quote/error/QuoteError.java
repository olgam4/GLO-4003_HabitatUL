package ca.ulaval.glo4003.underwriting.domain.quote.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class QuoteError extends BaseError {
  public QuoteError(String error, String message) {
    super(error, message);
  }
}
