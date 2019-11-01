package ca.ulaval.glo4003.gateway.presentation.common.handling;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({"error", "message", "messages"})
public class ErrorMessage {
  private final String error;
  private final String message;
  private final Set<String> messages;

  public ErrorMessage(String error, String message) {
    this.error = error;
    this.message = message;
    this.messages = null;
  }

  public ErrorMessage(String error, Set<String> messages) {
    this.error = error;
    this.message = null;
    this.messages = messages;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public Set<String> getMessages() {
    return messages;
  }
}
