package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.InvalidEffectiveDateError;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;

public class InvalidEffectiveDateErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new InvalidEffectiveDateError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_EFFECTIVE_DATE";
  }

  @Override
  public String getErrorDescriptionMatcher() {
    return "sorry, quote can only be issued for the incoming year";
  }
}
