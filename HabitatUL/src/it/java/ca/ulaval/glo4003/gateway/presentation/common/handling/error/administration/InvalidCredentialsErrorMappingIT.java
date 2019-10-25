package ca.ulaval.glo4003.gateway.presentation.common.handling.error.administration;

import ca.ulaval.glo4003.administration.application.user.error.InvalidCredentialsError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class InvalidCredentialsErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new InvalidCredentialsError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.UNAUTHORIZED.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_CREDENTIALS";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, the provided username/password combination is invalid";
  }
}
