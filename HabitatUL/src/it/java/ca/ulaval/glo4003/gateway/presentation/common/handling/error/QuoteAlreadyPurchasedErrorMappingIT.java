package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;

import javax.ws.rs.core.Response;

public class QuoteAlreadyPurchasedErrorMappingIT extends ErrorMappingIT {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Override
  public Throwable getError() {
    return new QuoteAlreadyPurchasedError(QUOTE_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_ALREADY_PURCHASED";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return String.format(
        "sorry, quote with id <%s> has already been purchased", QUOTE_ID.toRepresentation());
  }
}
