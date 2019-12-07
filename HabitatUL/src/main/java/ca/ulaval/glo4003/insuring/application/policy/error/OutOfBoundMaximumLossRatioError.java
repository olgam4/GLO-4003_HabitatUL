package ca.ulaval.glo4003.insuring.application.policy.error;

import static ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceImpl.GREATEST_MAXIMUM_LOSS_RATIO;
import static ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceImpl.SMALLEST_MAXIMUM_LOSS_RATIO;

public class OutOfBoundMaximumLossRatioError extends PolicyAppServiceError {
  private static final String ERROR = "OUT_OF_BOUND_MAXIMUM_LOSS_RATIO";
  private static final String MESSAGE =
      "sorry, maximum loss ratio must be contained between <%s> and <%s>";

  public OutOfBoundMaximumLossRatioError() {
    super(
        ERROR,
        String.format(
            MESSAGE,
            SMALLEST_MAXIMUM_LOSS_RATIO.getValue(),
            GREATEST_MAXIMUM_LOSS_RATIO.getValue()));
  }
}
