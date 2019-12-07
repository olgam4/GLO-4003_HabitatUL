package ca.ulaval.glo4003.insuring.infrastructure.policy.lossratio;

import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurationIT;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurer;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioProvider;

public class InMemoryMaximumLossRatioConfigurationIT extends MaximumLossRatioConfigurationIT {
  private InMemoryMaximumLossRatioConfiguration maximumLossRatioConfiguration =
      new InMemoryMaximumLossRatioConfiguration();

  @Override
  public MaximumLossRatioProvider createProvider() {
    return maximumLossRatioConfiguration;
  }

  @Override
  public MaximumLossRatioConfigurer createConfigurer() {
    return maximumLossRatioConfiguration;
  }
}
