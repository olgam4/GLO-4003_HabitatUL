package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidAmountError extends DeserializationError {
  private static final String ERROR = "INVALID_AMOUNT";
  private static final String MESSAGE = "sorry, <%s> is not a valid amount value";

  public InvalidAmountError(String amount) {
    super(ERROR, String.format(MESSAGE, amount));
  }
}
