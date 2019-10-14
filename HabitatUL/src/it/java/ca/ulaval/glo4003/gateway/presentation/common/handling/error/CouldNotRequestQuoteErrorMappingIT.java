package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;

import javax.ws.rs.core.Response;

public class CouldNotRequestQuoteErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new CouldNotRequestQuoteError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "COULD_NOT_REQUEST_QUOTE";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return "sorry, something went wrong while trying to request your quote";
  }
}
