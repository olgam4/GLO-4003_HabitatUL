package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;

public interface MaximumLossRatioConfigurer {
  LossRatio DEFAULT_MAXIMUM_LOSS_RATIO = new LossRatio(2f);

  void configureMaximumLossRatio(LossRatio lossRatio);
}
