package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityError;

import javax.ws.rs.core.Response;

public class QuoteCivilLiabilityErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteCivilLiabilityError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_CIVIL_LIABILITY";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return "sorry, the requested civil liability coverage amount is not allowed based on your risk exposure";
  }
}
