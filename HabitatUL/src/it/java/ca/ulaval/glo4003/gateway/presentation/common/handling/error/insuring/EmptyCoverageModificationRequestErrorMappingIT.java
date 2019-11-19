package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyCoverageModificationRequestError;

import javax.ws.rs.core.Response;

public class EmptyCoverageModificationRequestErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new EmptyCoverageModificationRequestError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "EMPTY_COVERAGE_MODIFICATION_REQUEST";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you must provide at least one coverage modification to proceed";
  }
}
