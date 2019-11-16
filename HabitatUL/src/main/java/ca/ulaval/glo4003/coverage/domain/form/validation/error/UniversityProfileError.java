package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class UniversityProfileError extends FormValidationError {
  private static final String ERROR = "UNIVERSITY_PROFILE";
  private static final String MESSAGE =
      "sorry, we have not been able to confirm your university profile";

  public UniversityProfileError() {
    super(ERROR, MESSAGE);
  }
}
