package ca.ulaval.glo4003.coverage.domain.form.validationpart.error;

public class DifferentAdditionalInsuredError extends FormValidationError {
  private static final String ERROR = "DIFFERENT_ADDITIONAL_INSURED";
  private static final String MESSAGE =
      "sorry, named and additional insureds must be different people";

  public DifferentAdditionalInsuredError() {
    super(ERROR, MESSAGE);
  }
}
