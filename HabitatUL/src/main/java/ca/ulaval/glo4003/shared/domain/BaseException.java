package ca.ulaval.glo4003.shared.domain;

public class BaseException extends RuntimeException {
  private static final String ERROR = "BASE_EXCEPTION";
  private static final String MESSAGE = "sorry, something wrong happened";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
