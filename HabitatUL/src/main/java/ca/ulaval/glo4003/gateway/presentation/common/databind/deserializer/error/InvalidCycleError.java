package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidCycleError extends DeserializationError {
  private static final String ERROR = "INVALID_CYCLE";
  private static final String MESSAGE = "sorry, <%s> is not a valid cycle value";

  public InvalidCycleError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
