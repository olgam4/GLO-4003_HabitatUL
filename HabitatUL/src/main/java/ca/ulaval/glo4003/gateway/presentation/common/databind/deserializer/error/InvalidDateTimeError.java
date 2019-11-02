package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidDateTimeError extends DeserializationError {
  private static final String ERROR = "INVALID_DATE_TIME";
  private static final String MESSAGE = "sorry, <%s> is not a valid date time value";

  public InvalidDateTimeError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
