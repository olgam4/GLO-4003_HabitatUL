package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCoverageAmountError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class IncreasedCoverageAmountErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new IncreasedCoverageAmountError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INCREASED_COVERAGE_AMOUNT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you are only allowed to increase your coverage amount";
  }
}
