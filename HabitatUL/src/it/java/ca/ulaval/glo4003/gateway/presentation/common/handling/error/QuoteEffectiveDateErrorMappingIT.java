package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteEffectiveDateError;

import javax.ws.rs.core.Response;

public class QuoteEffectiveDateErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteEffectiveDateError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_EFFECTIVE_DATE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, quote can only be issued for the incoming year";
  }
}
