package ca.ulaval.glo4003.gateway.presentation.common.error;

import javax.ws.rs.core.Response.Status;

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
