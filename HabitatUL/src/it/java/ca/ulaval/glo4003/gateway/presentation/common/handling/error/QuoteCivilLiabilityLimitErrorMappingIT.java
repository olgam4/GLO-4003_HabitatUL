package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityLimitError;

import javax.ws.rs.core.Response;

public class QuoteCivilLiabilityLimitErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteCivilLiabilityLimitError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_CIVIL_LIABILITY_LIMIT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, the requested civil liability limit is not allowed based on your risk exposure";
  }
}
