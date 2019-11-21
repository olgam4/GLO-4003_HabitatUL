package ca.ulaval.glo4003.insuring.domain.policy.error;

public class CannotTriggerRenewalBeforeRenewalPeriodError extends PolicyError {
  private static final String ERROR = "CANNOT_TRIGGER_RENEWAL_BEFORE_RENEWAL_PERIOD";
  private static final String MESSAGE = "sorry, you can't trigger renewal before renewal period";

  public CannotTriggerRenewalBeforeRenewalPeriodError() {
    super(ERROR, MESSAGE);
  }
}
