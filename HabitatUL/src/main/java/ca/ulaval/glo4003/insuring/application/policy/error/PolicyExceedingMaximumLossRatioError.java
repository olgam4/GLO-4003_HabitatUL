package ca.ulaval.glo4003.insuring.application.policy.error;

public class PolicyExceedingMaximumLossRatioError extends PolicyAppServiceError {
  private static final String ERROR = "POLICY_EXCEEDING_MAXIMUM_LOSS_RATIO";
  private static final String MESSAGE =
      "sorry, can't open claim because underlying policy exceeds the maximum loss ratio";

  public PolicyExceedingMaximumLossRatioError() {
    super(ERROR, MESSAGE);
  }
}
