package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteDifferentAdditionalInsuredError;

import javax.ws.rs.core.Response;

public class QuoteDifferentAdditionalInsuredErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteDifferentAdditionalInsuredError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_DIFFERENT_ADDITIONAL_INSURED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, named and additional insureds must be different people";
  }
}
