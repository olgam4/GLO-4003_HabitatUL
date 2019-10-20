package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentInformationError;

import javax.ws.rs.core.Response;

public class QuoteStudentInformationErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new QuoteStudentInformationError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "QUOTE_STUDENT_INFORMATION";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, we have not been able to confirm your student information";
  }
}
