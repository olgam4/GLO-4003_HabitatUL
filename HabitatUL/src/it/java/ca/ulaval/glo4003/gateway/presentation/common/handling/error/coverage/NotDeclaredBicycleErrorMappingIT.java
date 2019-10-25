package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class NotDeclaredBicycleErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new NotDeclaredBicycleError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "NOT_DECLARED_BICYCLE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you can not claim the loss of a bike that was not declared on your insurance policy";
  }
}
