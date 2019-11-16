package ca.ulaval.glo4003.helper.coverage.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory;
import ca.ulaval.glo4003.helper.shared.EnumSampler;

import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;
import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;

public class CoverageCategoryGenerator {
  private static final List<CoverageCategory> BASE_COVERAGE_CATEGORIES =
      Arrays.asList(PERSONAL_PROPERTY, CIVIL_LIABILITY);

  private CoverageCategoryGenerator() {}

  public static CoverageCategory createBaseCoverageCategory() {
    return EnumSampler.sampleValue(BASE_COVERAGE_CATEGORIES);
  }

  public static CoverageCategory createAdditionalCoverageCategory() {
    return EnumSampler.sample(CoverageCategory.class, BASE_COVERAGE_CATEGORIES);
  }

  public static CoverageCategory createCoverageCategory() {
    return EnumSampler.sample(CoverageCategory.class);
  }
}
