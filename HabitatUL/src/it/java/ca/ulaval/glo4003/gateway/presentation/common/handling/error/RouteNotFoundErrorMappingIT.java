package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class RouteNotFoundErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new NotFoundException();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.NOT_FOUND.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "ROUTE_NOT_FOUND";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, this route does not exist";
  }
}
