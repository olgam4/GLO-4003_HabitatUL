package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidPostalCodeError extends DeserializationError {
  private static final String ERROR = "INVALID_POSTAL_CODE";
  private static final String MESSAGE = "<%s> is not a valid postal code value";

  public InvalidPostalCodeError(String invalidPostalCode) {
    super(ERROR, String.format(MESSAGE, invalidPostalCode));
  }
}
