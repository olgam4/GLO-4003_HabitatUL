package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyLossDeclarationsError;

import javax.ws.rs.core.Response;

public class EmptyLossDeclarationsErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new EmptyLossDeclarationsError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "EMPTY_LOSS_DECLARATIONS";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you cannot open a claim with empty loss declarations";
  }
}
