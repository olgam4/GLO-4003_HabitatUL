package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;

public interface MaximumLossRatioProvider {
  LossRatio getMaximumLossRatio();
}
