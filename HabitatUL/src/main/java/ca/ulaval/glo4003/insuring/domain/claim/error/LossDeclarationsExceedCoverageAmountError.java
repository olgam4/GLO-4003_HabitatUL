package ca.ulaval.glo4003.insuring.domain.claim.error;

public class LossDeclarationsExceedCoverageAmountError extends ClaimError {
  private static final String ERROR = "LOSS_DECLARATIONS_EXCEED_COVERAGE_AMOUNT";
  private static final String MESSAGE =
      "sorry, you can not open a claim which exceeds the policy coverage amount";

  public LossDeclarationsExceedCoverageAmountError() {
    super(ERROR, MESSAGE);
  }
}
