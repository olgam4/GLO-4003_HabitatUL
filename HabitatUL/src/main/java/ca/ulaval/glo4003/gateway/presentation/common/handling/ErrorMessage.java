package ca.ulaval.glo4003.gateway.presentation.common.handling;

public class ErrorMessage {
  private final String error;
  private final String description;

  public ErrorMessage(String error, String description) {
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
