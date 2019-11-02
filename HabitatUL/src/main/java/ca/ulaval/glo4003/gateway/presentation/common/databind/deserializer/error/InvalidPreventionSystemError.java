package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidPreventionSystemError extends DeserializationError {
  private static final String ERROR = "INVALID_PREVENTION_SYSTEM";
  private static final String MESSAGE = "sorry, <%s> is not a valid prevention system value";

  public InvalidPreventionSystemError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
