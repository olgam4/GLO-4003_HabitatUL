package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCivilLiabilityLimitError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class IncreasedCivilLiabilityLimitErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new IncreasedCivilLiabilityLimitError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INCREASED_CIVIL_LIABILITY_LIMIT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, you are only allowed to increase your civil liability limit";
  }
}
