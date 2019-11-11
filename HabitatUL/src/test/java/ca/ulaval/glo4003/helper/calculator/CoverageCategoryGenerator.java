package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.helper.EnumSampler;

public class CoverageCategoryGenerator {
  private CoverageCategoryGenerator() {}

  public static CoverageCategory createCoverageCategory() {
    return EnumSampler.sample(CoverageCategory.class);
  }
}
