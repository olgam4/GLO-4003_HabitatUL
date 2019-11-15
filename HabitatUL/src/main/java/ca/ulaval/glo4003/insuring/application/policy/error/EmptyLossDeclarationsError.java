package ca.ulaval.glo4003.insuring.application.policy.error;

public class EmptyLossDeclarationsError extends PolicyAppServiceError {
  private static final String ERROR = "EMPTY_LOSS_DECLARATIONS";
  private static final String MESSAGE =
      "sorry, you cannot open a claim with empty loss declarations";

  public EmptyLossDeclarationsError() {
    super(ERROR, MESSAGE);
  }
}
