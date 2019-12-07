package ca.ulaval.glo4003.insuring.infrastructure.policy.lossratio;

import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurer;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioProvider;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;

public class InMemoryMaximumLossRatioConfiguration
    implements MaximumLossRatioProvider, MaximumLossRatioConfigurer {
  private LossRatio maximumLossRatio = DEFAULT_MAXIMUM_LOSS_RATIO;

  @Override
  public LossRatio getMaximumLossRatio() {
    return maximumLossRatio;
  }

  @Override
  public void configureMaximumLossRatio(LossRatio lossRatio) {
    maximumLossRatio = lossRatio;
  }
}
