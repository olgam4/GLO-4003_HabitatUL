package ca.ulaval.glo4003.shared.domain;

public class BaseError extends RuntimeException implements Error {
  private static final String ERROR = "ERROR";
  private static final String MESSAGE = "sorry, something wrong happened";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
