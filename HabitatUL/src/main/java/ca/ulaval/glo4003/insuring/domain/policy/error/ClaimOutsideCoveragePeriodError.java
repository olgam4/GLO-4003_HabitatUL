package ca.ulaval.glo4003.insuring.domain.policy.error;

public class ClaimOutsideCoveragePeriodError extends PolicyError {
  private static final String ERROR = "CLAIM_OUTSIDE_COVERAGE_PERIOD";
  private static final String MESSAGE =
      "sorry, you can not open a claim outside the policy coverage period";

  public ClaimOutsideCoveragePeriodError() {
    super(ERROR, MESSAGE);
  }
}
