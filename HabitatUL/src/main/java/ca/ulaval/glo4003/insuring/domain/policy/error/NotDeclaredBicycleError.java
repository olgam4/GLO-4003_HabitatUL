package ca.ulaval.glo4003.insuring.domain.policy.error;

public class NotDeclaredBicycleError extends PolicyError {
  private static final String ERROR = "NOT_DECLARED_BICYCLE";
  private static final String MESSAGE =
      "sorry, you can not claim the loss of a bicycle that was not declared on your policy";

  public NotDeclaredBicycleError() {
    super(ERROR, MESSAGE);
  }
}
