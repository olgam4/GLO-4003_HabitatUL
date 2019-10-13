package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidSinisterTypeError extends DeserializationError {
  private static final String ERROR = "INVALID_SINISTER_TYPE";
  private static final String MESSAGE = "<%s> is not a valid sinister type value";

  public InvalidSinisterTypeError(String invalidSinisterType) {
    super(ERROR, String.format(MESSAGE, invalidSinisterType));
  }
}
