package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidYearError extends DeserializationError {
  private static final String ERROR = "INVALID_YEAR";
  private static final String MESSAGE = "sorry, <%s> is not a valid year value";

  public InvalidYearError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
