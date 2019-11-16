package ca.ulaval.glo4003.coverage.domain.form.validation.part.error;

public class StudentNamedInsuredError extends FormValidationError {
  private static final String ERROR = "STUDENT_NAMED_INSURED";
  private static final String MESSAGE = "sorry, the named insured must have a university profile";

  public StudentNamedInsuredError() {
    super(ERROR, MESSAGE);
  }
}
