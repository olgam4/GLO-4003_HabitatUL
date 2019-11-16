package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveBicyclePriceError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class PositiveBicyclePriceErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new PositiveBicyclePriceError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POSITIVE_BICYCLE_PRICE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, bicycle price must be greater than 0";
  }
}
