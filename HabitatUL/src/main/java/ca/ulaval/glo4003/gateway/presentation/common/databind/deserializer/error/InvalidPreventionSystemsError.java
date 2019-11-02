package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidPreventionSystemsError extends DeserializationError {
  private static final String ERROR = "INVALID_PREVENTION_SYSTEMS";
  private static final String MESSAGE = "sorry, <%s> is not a valid prevention systems value";

  public InvalidPreventionSystemsError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
