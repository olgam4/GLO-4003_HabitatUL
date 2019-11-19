package ca.ulaval.glo4003.insuring.application.policy.error;

public class EmptyCoverageModificationRequestError extends PolicyAppServiceError {
  private static final String ERROR = "EMPTY_COVERAGE_MODIFICATION_REQUEST";
  private static final String MESSAGE =
      "sorry, you must provide at least one coverage modification to proceed";

  public EmptyCoverageModificationRequestError() {
    super(ERROR, MESSAGE);
  }
}
