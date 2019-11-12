package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.CivilLiabilityLimitError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class CivilLiabilityLimitErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new CivilLiabilityLimitError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CIVIL_LIABILITY_LIMIT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, the requested civil liability limit is not allowed based on your risk exposure";
  }
}