package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidZipCodeError extends DeserializationError {
  private static final String ERROR = "INVALID_ZIP_CODE";
  private static final String MESSAGE = "sorry, <%s> is not a valid zip code value";

  public InvalidZipCodeError(String invalidZipCode) {
    super(ERROR, String.format(MESSAGE, invalidZipCode));
  }
}
