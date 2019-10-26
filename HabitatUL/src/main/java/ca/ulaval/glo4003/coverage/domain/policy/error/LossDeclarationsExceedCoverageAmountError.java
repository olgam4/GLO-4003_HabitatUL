package ca.ulaval.glo4003.coverage.domain.policy.error;

public class LossDeclarationsExceedCoverageAmountError extends PolicyError {
  private static final String ERROR = "LOSS_DECLARATIONS_EXCEED_COVERAGE_AMOUNT";
  private static final String MESSAGE =
      "sorry, you can not open a claim which exceeds the policy coverage amount";

  public LossDeclarationsExceedCoverageAmountError() {
    super(ERROR, MESSAGE);
  }
}
