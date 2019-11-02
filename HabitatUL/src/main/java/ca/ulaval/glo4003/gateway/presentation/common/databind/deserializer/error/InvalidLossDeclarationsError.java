package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidLossDeclarationsError extends DeserializationError {
  private static final String ERROR = "INVALID_LOSS_DECLARATIONS";
  private static final String MESSAGE = "sorry, <%s> is not a valid loss declarations value";

  public InvalidLossDeclarationsError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
