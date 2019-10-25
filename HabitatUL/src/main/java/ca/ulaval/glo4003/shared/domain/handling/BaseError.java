package ca.ulaval.glo4003.shared.domain.handling;

public abstract class BaseError extends RuntimeException implements Error {
  private final String error;
  private final String message;

  public BaseError(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}
