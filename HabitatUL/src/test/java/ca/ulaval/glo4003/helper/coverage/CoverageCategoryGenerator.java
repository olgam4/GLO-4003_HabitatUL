package ca.ulaval.glo4003.helper.coverage;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.helper.shared.EnumSampler;

public class CoverageCategoryGenerator {
  private CoverageCategoryGenerator() {}

  public static CoverageCategory createCoverageCategory() {
    return EnumSampler.sample(CoverageCategory.class);
  }
}
