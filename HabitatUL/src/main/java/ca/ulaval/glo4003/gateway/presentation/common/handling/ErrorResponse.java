package ca.ulaval.glo4003.gateway.presentation.common.handling;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.ws.rs.core.Response.Status;

@JsonPropertyOrder({"status", "message"})
public class ErrorResponse {
  private final Status status;
  private final ErrorMessage message;

  public ErrorResponse(Status status, String error, String description) {
    this.status = status;
    this.message = new ErrorMessage(error, description);
  }

  public Status getStatus() {
    return status;
  }

  public ErrorMessage getMessage() {
    return message;
  }
}
