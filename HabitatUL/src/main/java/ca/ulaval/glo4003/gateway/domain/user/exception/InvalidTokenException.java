package ca.ulaval.glo4003.gateway.domain.user.exception;

public class InvalidTokenException extends UserException {
  private static final String ERROR = "INVALID_TOKEN";
  private static final String MESSAGE = "token has invalid signature";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
