package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidCivilLiabilityLimitError extends DeserializationError {
  private static final String ERROR = "INVALID_CIVIL_LIABILITY_LIMIT";
  private static final String MESSAGE = "sorry, <%s> is not a valid civil liability limit value";

  public InvalidCivilLiabilityLimitError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
