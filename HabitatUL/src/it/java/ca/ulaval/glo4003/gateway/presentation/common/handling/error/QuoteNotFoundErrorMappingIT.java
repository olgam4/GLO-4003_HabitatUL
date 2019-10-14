package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import javax.ws.rs.core.Response;

public class QuoteNotFoundErrorMappingIT extends ErrorMappingIT {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Override
  public Throwable getError() {
    return new QuoteNotFoundError(QUOTE_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.NOT_FOUND.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_NOT_FOUND";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return String.format("sorry, can't find quote with id <%s>", QUOTE_ID.toRepresentation());
  }
}
