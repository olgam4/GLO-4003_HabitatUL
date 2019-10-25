package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteUniversityProfileError;

import javax.ws.rs.core.Response;

public class QuoteUniversityProfileErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteUniversityProfileError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_UNIVERSITY_PROFILE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, we have not been able to confirm your university profile";
  }
}
