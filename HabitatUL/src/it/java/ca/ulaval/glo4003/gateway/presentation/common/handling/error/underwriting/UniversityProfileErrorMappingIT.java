package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.UniversityProfileError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class UniversityProfileErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new UniversityProfileError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "UNIVERSITY_PROFILE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, we have not been able to confirm your university profile";
  }
}
