package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CoverageModificationForm extends ValueObject {
  private final Amount coverageAmount;
  private final CivilLiabilityLimit civilLiabilityLimit;
  private final Integer numberOfUnits;
  private final CoverageDetails currentCoverageDetails;
  private final PremiumDetails currentPremiumDetails;

  public CoverageModificationForm(
      Amount coverageAmount,
      CivilLiabilityLimit civilLiabilityLimit,
      Integer numberOfUnits,
      CoverageDetails currentCoverageDetails,
      PremiumDetails currentPremiumDetails) {
    this.coverageAmount = coverageAmount;
    this.civilLiabilityLimit = civilLiabilityLimit;
    this.numberOfUnits = numberOfUnits;
    this.currentCoverageDetails = currentCoverageDetails;
    this.currentPremiumDetails = currentPremiumDetails;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }

  public int getNumberOfUnits() {
    return numberOfUnits;
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return currentCoverageDetails;
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return currentPremiumDetails;
  }

  public boolean isFilled() {
    return coverageAmount != null || civilLiabilityLimit != null;
  }
}
