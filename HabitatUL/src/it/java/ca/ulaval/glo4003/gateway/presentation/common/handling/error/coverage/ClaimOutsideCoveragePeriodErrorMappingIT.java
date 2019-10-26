package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class ClaimOutsideCoveragePeriodErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new ClaimOutsideCoveragePeriodError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CLAIM_OUTSIDE_COVERAGE_PERIOD";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you can not open a claim outside the policy coverage period";
  }
}
