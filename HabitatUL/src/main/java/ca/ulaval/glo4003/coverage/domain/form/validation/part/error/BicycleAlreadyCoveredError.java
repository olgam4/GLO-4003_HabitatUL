package ca.ulaval.glo4003.coverage.domain.form.validation.part.error;

import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyError;

public class BicycleAlreadyCoveredError extends PolicyError {
  private static String ERROR = "BICYCLE_ALREADY_COVERED";
  private static String MESSAGE = "sorry, you cannot insure more than one bicycle on your policy";

  public BicycleAlreadyCoveredError() {
    super(ERROR, MESSAGE);
  }
}
