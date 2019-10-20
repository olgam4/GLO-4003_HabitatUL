package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidCivilLiabilityAmountError extends DeserializationError {
  private static final String ERROR = "INVALID_CIVIL_LIABILITY_AMOUNT";
  private static final String MESSAGE = "sorry, <%s> is not a valid civil liability amount value";

  public InvalidCivilLiabilityAmountError(String invalidCivilLiabilityAmount) {
    super(ERROR, String.format(MESSAGE, invalidCivilLiabilityAmount));
  }
}
