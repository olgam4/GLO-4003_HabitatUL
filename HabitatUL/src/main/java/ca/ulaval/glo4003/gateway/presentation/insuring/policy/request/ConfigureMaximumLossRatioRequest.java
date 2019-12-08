package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;

import javax.validation.constraints.NotNull;

public class ConfigureMaximumLossRatioRequest {
  @NotNull private LossRatio maximumLossRatio;

  private ConfigureMaximumLossRatioRequest() {}

  public ConfigureMaximumLossRatioRequest(LossRatio maximumLossRatio) {
    this.maximumLossRatio = maximumLossRatio;
  }

  public LossRatio getMaximumLossRatio() {
    return maximumLossRatio;
  }
}
