package ca.ulaval.glo4003.gateway.presentation.common.handling;

import javax.ws.rs.core.Response.Status;

public class ErrorResponse {
  private final Status status;
  private final ErrorMessage message;

  public ErrorResponse(Status status, String error, String message) {
    this.status = status;
    this.message = new ErrorMessage(error, message);
  }

  public Status getStatus() {
    return status;
  }

  public ErrorMessage getMessage() {
    return message;
  }
}
