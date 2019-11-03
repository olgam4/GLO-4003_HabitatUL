package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuotePositiveCoverageAmountError;

import javax.ws.rs.core.Response;

public class QuotePositiveCoverageAmountErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuotePositiveCoverageAmountError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_POSITIVE_COVERAGE_AMOUNT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, coverage amount must be greater than 0";
  }
}
