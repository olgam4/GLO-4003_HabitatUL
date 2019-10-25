package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidDateError extends DeserializationError {
  private static final String ERROR = "INVALID_DATE";
  private static final String MESSAGE = "sorry, <%s> is not a valid date value";

  public InvalidDateError(String invalidDate) {
    super(ERROR, String.format(MESSAGE, invalidDate));
  }
}
