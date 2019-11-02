package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidSinisterTypeError extends DeserializationError {
  private static final String ERROR = "INVALID_SINISTER_TYPE";
  private static final String MESSAGE = "sorry, <%s> is not a valid sinister type value";

  public InvalidSinisterTypeError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
