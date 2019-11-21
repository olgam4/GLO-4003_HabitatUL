package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.AnotherRenewalAlreadyAcceptedError;

import javax.ws.rs.core.Response;

public class AnotherRenewalAlreadyAcceptedErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new AnotherRenewalAlreadyAcceptedError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "ANOTHER_RENEWAL_ALREADY_ACCEPTED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you already have accepted another renewal offer";
  }
}
