package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.DifferentAdditionalInsuredError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class DifferentAdditionalInsuredErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new DifferentAdditionalInsuredError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "DIFFERENT_ADDITIONAL_INSURED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, named and additional insureds must be different people";
  }
}
