package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ConfigureMaximumLossRatioRequest;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import com.github.javafaker.Faker;

public class LossRatioGenerator {
  private static final int MAX_NUMBER_OF_DECIMALS = 5;

  private LossRatioGenerator() {}

  public static ConfigureMaximumLossRatioRequest createConfigureMaximumLossRatioRequest() {
    return new ConfigureMaximumLossRatioRequest(createLossRatio());
  }

  public static LossRatio createLossRatio() {
    return new LossRatio((float) Faker.instance().number().randomDigitNotZero());
  }

  public static LossRatio createLossRatioSmallerThan(LossRatio lossRatio) {
    return new LossRatio(
        (float)
            Faker.instance()
                .number()
                .randomDouble(MAX_NUMBER_OF_DECIMALS, 0, (int) lossRatio.getValue() - 1));
  }

  public static LossRatio createLossRatioGreaterThan(LossRatio lossRatio) {
    return new LossRatio(
        (float)
            Faker.instance()
                .number()
                .randomDouble(
                    MAX_NUMBER_OF_DECIMALS, (int) lossRatio.getValue() + 1, Integer.MAX_VALUE));
  }

  public static LossRatio createLossRatioBetween(LossRatio minLossRatio, LossRatio maxLossRatio) {
    return new LossRatio(
        (float)
            Faker.instance()
                .number()
                .randomDouble(
                    MAX_NUMBER_OF_DECIMALS,
                    (int) minLossRatio.getValue() + 1,
                    (int) maxLossRatio.getValue() - 1));
  }
}
