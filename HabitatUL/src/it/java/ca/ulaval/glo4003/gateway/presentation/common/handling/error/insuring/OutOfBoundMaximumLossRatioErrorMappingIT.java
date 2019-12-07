package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.application.policy.error.OutOfBoundMaximumLossRatioError;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceImpl.GREATEST_MAXIMUM_LOSS_RATIO;
import static ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceImpl.SMALLEST_MAXIMUM_LOSS_RATIO;

public class OutOfBoundMaximumLossRatioErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new OutOfBoundMaximumLossRatioError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "OUT_OF_BOUND_MAXIMUM_LOSS_RATIO";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, maximum loss ratio must be contained between <%s> and <%s>",
        SMALLEST_MAXIMUM_LOSS_RATIO.getValue(), GREATEST_MAXIMUM_LOSS_RATIO.getValue());
  }
}
