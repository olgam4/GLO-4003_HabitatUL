package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class IncreasedCivilLiabilityLimitError extends FormValidationError {
  private static final String ERROR = "INCREASED_CIVIL_LIABILITY_LIMIT";
  private static final String MESSAGE =
      "sorry, you are only allowed to increase your civil liability limit";

  public IncreasedCivilLiabilityLimitError() {
    super(ERROR, MESSAGE);
  }
}
