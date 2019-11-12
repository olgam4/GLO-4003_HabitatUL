package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;

import javax.ws.rs.core.Response;

public class CouldNotOpenClaimErrorMappingIT extends ErrorMappingIT {
  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public Throwable getError() {
    return new CouldNotOpenClaimError();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "COULD_NOT_OPEN_CLAIM";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, something went wrong while trying to open your claim";
  }
}
