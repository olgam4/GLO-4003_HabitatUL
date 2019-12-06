package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.application.premium.error.CannotComputeModificationPremiumAdjustmentError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;

import javax.ws.rs.core.Response;

public class CannotComputeModificationPremiumAdjustmentErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new CannotComputeModificationPremiumAdjustmentError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CANNOT_COMPUTE_MODIFICATION_PREMIUM_ADJUSTMENT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, something went wrong while computing modification premium adjustment";
  }
}
