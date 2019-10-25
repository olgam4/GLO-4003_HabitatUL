package ca.ulaval.glo4003.administration.domain.user.error;

public class UnauthorizedError extends UserError {
  private static final String ERROR = "UNAUTHORIZED";
  private static final String MESSAGE =
      "sorry, you do not have necessary rights to access this resource";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
