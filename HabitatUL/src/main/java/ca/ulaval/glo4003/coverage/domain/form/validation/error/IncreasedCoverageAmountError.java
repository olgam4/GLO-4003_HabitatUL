package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class IncreasedCoverageAmountError extends FormValidationError {
  private static final String ERROR = "INCREASED_COVERAGE_AMOUNT";
  private static final String MESSAGE =
      "sorry, you are only allowed to increase your coverage amount";

  public IncreasedCoverageAmountError() {
    super(ERROR, MESSAGE);
  }
}
