package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidAuthorityNumber extends DeserializationError {
  private static final String ERROR = "INVALID_AUTHORITY_NUMBER";
  private static final String MESSAGE = "sorry, <%s> is not a valid authority number";

  public InvalidAuthorityNumber(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
