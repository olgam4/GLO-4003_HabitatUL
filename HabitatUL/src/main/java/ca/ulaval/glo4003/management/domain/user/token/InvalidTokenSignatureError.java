package ca.ulaval.glo4003.management.domain.user.token;

import ca.ulaval.glo4003.management.domain.user.exception.UserError;

public class InvalidTokenSignatureError extends UserError {
  private static final String ERROR = "INVALID_TOKEN_SIGNATURE";
  private static final String MESSAGE = "token has invalid signature";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
