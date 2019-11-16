package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class PositiveCoverageAmountErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new PositiveCoverageAmountError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POSITIVE_COVERAGE_AMOUNT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, coverage amount must be greater than 0";
  }
}
