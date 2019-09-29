package ca.ulaval.glo4003.gateway.presentation.exception;

import javax.ws.rs.core.Response.Status;

public class ExceptionResponse {
  private final Status status;
  private final ExceptionMessage message;

  public ExceptionResponse(Status status, String error, String description) {
    this.status = status;
    this.message = new ExceptionMessage(error, description);
  }

  public Status getStatus() {
    return status;
  }

  public ExceptionMessage getMessage() {
    return message;
  }
}
