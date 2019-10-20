package ca.ulaval.glo4003.gateway.presentation.common.handling;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"error", "message"})
public class ErrorMessage {
  private final String error;
  private final String message;

  public ErrorMessage(String error, String message) {
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
