package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidGenderError extends DeserializationError {
  private static final String ERROR = "INVALID_GENDER";
  private static final String MESSAGE = "sorry, <%s> is not a valid gender value";

  public InvalidGenderError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
