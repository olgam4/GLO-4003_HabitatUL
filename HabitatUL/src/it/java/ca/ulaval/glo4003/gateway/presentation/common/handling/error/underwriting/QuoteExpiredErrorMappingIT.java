package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator;

import javax.ws.rs.core.Response;

public class QuoteExpiredErrorMappingIT extends ErrorMappingIT {
  private static final QuoteId QUOTE_ID = QuoteGenerator.createQuoteId();

  @Override
  public Throwable getError() {
    return new QuoteExpiredError(QUOTE_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_EXPIRED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, quote with id <%s> is expired", QUOTE_ID.toRepresentation());
  }
}
