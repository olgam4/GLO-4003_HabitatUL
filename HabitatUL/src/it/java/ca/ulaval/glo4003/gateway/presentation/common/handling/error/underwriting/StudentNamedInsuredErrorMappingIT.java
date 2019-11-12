package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.StudentNamedInsuredError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class StudentNamedInsuredErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new StudentNamedInsuredError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "STUDENT_NAMED_INSURED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, the named insured must have a university profile";
  }
}
