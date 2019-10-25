package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class UnauthorizedErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new UnauthorizedError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.UNAUTHORIZED.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "UNAUTHORIZED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you do not have necessary rights to access this resource";
  }
}
