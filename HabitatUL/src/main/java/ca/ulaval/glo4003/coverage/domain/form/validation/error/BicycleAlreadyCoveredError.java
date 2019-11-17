package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class BicycleAlreadyCoveredError extends FormValidationError {
  private static String ERROR = "BICYCLE_ALREADY_COVERED";
  private static String MESSAGE = "sorry, you can't insure more than one bicycle on your policy";

  public BicycleAlreadyCoveredError() {
    super(ERROR, MESSAGE);
  }
}
