package ca.ulaval.glo4003.helper.coverage;

import ca.ulaval.glo4003.coverage.application.CoverageDto;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;

public class CoverageGenerator {
  private CoverageGenerator() {}

  public static CoverageDto createCoverageDto() {
    return new CoverageDto(createCoverageDetails(), createPremiumDetails());
  }
}
