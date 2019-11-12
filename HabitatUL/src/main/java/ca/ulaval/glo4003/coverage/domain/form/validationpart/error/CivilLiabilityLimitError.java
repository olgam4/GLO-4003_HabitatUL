package ca.ulaval.glo4003.coverage.domain.form.validationpart.error;

public class CivilLiabilityLimitError extends FormValidationError {
  private static final String ERROR = "CIVIL_LIABILITY_LIMIT";
  private static final String MESSAGE =
      "sorry, the requested civil liability limit is not allowed based on your risk exposure";

  public CivilLiabilityLimitError() {
    super(ERROR, MESSAGE);
  }
}
