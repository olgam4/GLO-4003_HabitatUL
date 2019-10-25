package ca.ulaval.glo4003.gateway.presentation.common.handling.error.administration;

import ca.ulaval.glo4003.administration.application.user.error.CouldNotCreateUserError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class CouldNotCreateUserErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new CouldNotCreateUserError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "COULD_NOT_CREATE_USER";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, something went wrong while trying to create your user profile";
  }
}
