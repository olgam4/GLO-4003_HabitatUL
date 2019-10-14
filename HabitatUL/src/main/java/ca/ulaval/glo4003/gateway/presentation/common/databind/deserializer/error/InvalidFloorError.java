package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidFloorError extends DeserializationError {
  private static final String ERROR = "INVALID_FLOOR";
  private static final String MESSAGE = "sorry, <%s> is not a valid floor value";

  public InvalidFloorError(String invalidFloor) {
    super(ERROR, String.format(MESSAGE, invalidFloor));
  }
}
