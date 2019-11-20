package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CoverageRenewalForm extends ValueObject {
  private final Amount coverageAmount;
  private final CoverageDetails currentCoverageDetails;
  private final PremiumDetails currentPremiumDetails;

  public CoverageRenewalForm(
      Amount coverageAmount,
      CoverageDetails currentCoverageDetails,
      PremiumDetails currentPremiumDetails) {
    this.coverageAmount = coverageAmount;
    this.currentCoverageDetails = currentCoverageDetails;
    this.currentPremiumDetails = currentPremiumDetails;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return currentCoverageDetails;
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return currentPremiumDetails;
  }
}
