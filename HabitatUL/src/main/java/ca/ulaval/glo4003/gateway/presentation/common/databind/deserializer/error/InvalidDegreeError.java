package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidDegreeError extends DeserializationError {
  private static final String ERROR = "INVALID_DEGREE";
  private static final String MESSAGE = "sorry, <%s> is not a valid degree value";

  public InvalidDegreeError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
