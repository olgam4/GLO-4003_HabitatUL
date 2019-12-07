package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.policy.LossRatioGenerator.createLossRatio;
import static ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurer.DEFAULT_MAXIMUM_LOSS_RATIO;
import static org.junit.Assert.assertEquals;

public abstract class MaximumLossRatioConfigurationIT {
  private static final LossRatio OVERRIDDEN_MAXIMUM_LOSS_RATIO = createLossRatio();

  private MaximumLossRatioProvider provider;
  private MaximumLossRatioConfigurer configurer;

  @Before
  public void setUp() {
    provider = createProvider();
    configurer = createConfigurer();
  }

  @Test
  public void
      gettingMaximumLossRatio_withDefaultConfiguration_shouldProvideDefaultMaximumLossRatio() {
    LossRatio maximumLossRatio = provider.getMaximumLossRatio();

    assertEquals(DEFAULT_MAXIMUM_LOSS_RATIO, maximumLossRatio);
  }

  @Test
  public void
      gettingMaximumLossRatio_withOveridenConfiguration_shouldProvideOverriddenMaximumLossRatio() {
    configurer.configureMaximumLossRatio(OVERRIDDEN_MAXIMUM_LOSS_RATIO);

    LossRatio maximumLossRatio = provider.getMaximumLossRatio();

    assertEquals(OVERRIDDEN_MAXIMUM_LOSS_RATIO, maximumLossRatio);
  }

  public abstract MaximumLossRatioProvider createProvider();

  public abstract MaximumLossRatioConfigurer createConfigurer();
}
