package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidLossRatioError extends DeserializationError {
  private static final String ERROR = "INVALID_LOSS_RATIO";
  private static final String MESSAGE = "sorry, <%s> is not a valid loss ratio value";

  public InvalidLossRatioError(String invalidValue) {
    super(ERROR, String.format(MESSAGE, invalidValue));
  }
}
