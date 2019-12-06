package ca.ulaval.glo4003.coverage.application.premium.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class PremiumCalculatorError extends BaseError {
  public PremiumCalculatorError(String error, String message) {
    super(error, message);
  }

  public PremiumCalculatorError(String error, String message, Throwable cause) {
    super(error, message, cause);
  }
}
