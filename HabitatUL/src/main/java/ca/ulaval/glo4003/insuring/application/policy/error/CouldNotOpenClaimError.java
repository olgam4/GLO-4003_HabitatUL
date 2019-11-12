package ca.ulaval.glo4003.insuring.application.policy.error;

public class CouldNotOpenClaimError extends PolicyAppServiceError {
  private static final String ERROR = "COULD_NOT_OPEN_CLAIM";
  private static final String MESSAGE =
      "sorry, something went wrong while trying to open your claim";

  public CouldNotOpenClaimError() {
    super(ERROR, MESSAGE);
  }
}
