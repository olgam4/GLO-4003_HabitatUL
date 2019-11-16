package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.form.validation.part.error.BicycleAlreadyCoveredError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class BicycleAlreadyCoveredErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new BicycleAlreadyCoveredError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "BICYCLE_ALREADY_COVERED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you cannot insure more than one bicycle on your policy";
  }
}
