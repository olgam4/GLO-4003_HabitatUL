package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class NotMappedErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new RuntimeException();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "ERROR";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return "sorry, something wrong happened";
  }
}
