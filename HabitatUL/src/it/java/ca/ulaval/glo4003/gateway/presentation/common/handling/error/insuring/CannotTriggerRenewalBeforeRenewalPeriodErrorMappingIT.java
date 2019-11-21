package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.CannotTriggerRenewalBeforeRenewalPeriodError;

import javax.ws.rs.core.Response;

public class CannotTriggerRenewalBeforeRenewalPeriodErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new CannotTriggerRenewalBeforeRenewalPeriodError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CANNOT_TRIGGER_RENEWAL_BEFORE_RENEWAL_PERIOD";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you can't trigger renewal before renewal period";
  }
}
