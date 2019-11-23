package ca.ulaval.glo4003.shared.application.concurrency.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class ConcurrencyError extends BaseError {
  public ConcurrencyError(String error, String message) {
    super(error, message);
  }
}
