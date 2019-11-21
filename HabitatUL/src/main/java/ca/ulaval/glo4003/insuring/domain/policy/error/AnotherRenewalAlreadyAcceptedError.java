package ca.ulaval.glo4003.insuring.domain.policy.error;

public class AnotherRenewalAlreadyAcceptedError extends PolicyError {
  private static final String ERROR = "ANOTHER_RENEWAL_ALREADY_ACCEPTED";
  private static final String MESSAGE = "sorry, you already have accepted another renewal offer";

  public AnotherRenewalAlreadyAcceptedError() {
    super(ERROR, MESSAGE);
  }
}
