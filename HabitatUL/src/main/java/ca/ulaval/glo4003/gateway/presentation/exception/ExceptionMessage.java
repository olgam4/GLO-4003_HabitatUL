package ca.ulaval.glo4003.gateway.presentation.exception;

public class ExceptionMessage {
  private final String error;
  private final String description;

  public ExceptionMessage(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }
}
