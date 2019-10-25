package ca.ulaval.glo4003.underwriting.application.quote.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class QuoteAppServiceError extends BaseError {
  public QuoteAppServiceError(String error, String message) {
    super(error, message);
  }
}
