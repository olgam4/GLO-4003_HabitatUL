package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class CoverageDto extends DataTransferObject {
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;

  public CoverageDto(CoverageDetails coverageDetails, PremiumDetails premiumDetails) {
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
