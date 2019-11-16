package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory;
import ca.ulaval.glo4003.helper.shared.EnumSampler;

import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory.BASIC_BLOCK;

public class PremiumCategoryGenerator {
  private static final List<PremiumCategory> BASE_PREMIUM_CATEGORIES = Arrays.asList(BASIC_BLOCK);

  private PremiumCategoryGenerator() {}

  public static PremiumCategory createBasePremiumCategory() {
    return EnumSampler.sampleValue(BASE_PREMIUM_CATEGORIES);
  }

  public static PremiumCategory createAdditionalPremiumCategory() {
    return EnumSampler.sample(PremiumCategory.class, BASE_PREMIUM_CATEGORIES);
  }

  public static PremiumCategory createPremiumCategory() {
    return EnumSampler.sample(PremiumCategory.class);
  }
}
