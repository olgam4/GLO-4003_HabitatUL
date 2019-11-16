package ca.ulaval.glo4003.coverage.domain.form.validation.part.error;

public class PositiveCoverageAmountError extends FormValidationError {
  private static final String ERROR = "POSITIVE_COVERAGE_AMOUNT";
  private static final String MESSAGE = "sorry, coverage amount must be greater than 0";

  public PositiveCoverageAmountError() {
    super(ERROR, MESSAGE);
  }
}
