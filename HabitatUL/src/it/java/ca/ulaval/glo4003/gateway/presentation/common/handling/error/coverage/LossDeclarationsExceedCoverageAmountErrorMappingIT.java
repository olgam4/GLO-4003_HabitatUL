package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.LossDeclarationsExceedCoverageAmountError;

import javax.ws.rs.core.Response;

public class LossDeclarationsExceedCoverageAmountErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new LossDeclarationsExceedCoverageAmountError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "LOSS_DECLARATIONS_EXCEED_COVERAGE_AMOUNT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you can not open a claim which exceeds the policy coverage amount";
  }
}
