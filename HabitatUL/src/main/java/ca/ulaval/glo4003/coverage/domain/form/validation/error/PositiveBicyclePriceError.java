package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class PositiveBicyclePriceError extends FormValidationError {
  private static final String ERROR = "POSITIVE_BICYCLE_PRICE";
  private static final String MESSAGE = "sorry, bicycle price must be greater than 0";

  public PositiveBicyclePriceError() {
    super(ERROR, MESSAGE);
  }
}
