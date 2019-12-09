package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.application.policy.error.PolicyExceedingMaximumLossRatioError;

import javax.ws.rs.core.Response;

public class PolicyExceedingMaximumLossRatioErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new PolicyExceedingMaximumLossRatioError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POLICY_EXCEEDING_MAXIMUM_LOSS_RATIO";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, can't open claim because underlying policy exceeds the maximum loss ratio";
  }
}
