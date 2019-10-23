package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentNamedInsuredError;

import javax.ws.rs.core.Response;

public class QuoteStudentNamedInsuredErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteStudentNamedInsuredError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_STUDENT_NAMED_INSURED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, the named insured must have a university profile";
  }
}
