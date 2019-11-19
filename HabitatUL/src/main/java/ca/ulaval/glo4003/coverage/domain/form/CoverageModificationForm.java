package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CoverageModificationForm extends ValueObject {
  private final Amount personalPropertyCoverageAmount;
  private final CivilLiabilityLimit civilLiabilityLimit;
  private final CoverageDetails currentCoverageDetails;
  private final PremiumDetails currentPremiumDetails;

  public CoverageModificationForm(
      Amount personalPropertyCoverageAmount,
      CivilLiabilityLimit civilLiabilityLimit,
      CoverageDetails currentCoverageDetails,
      PremiumDetails currentPremiumDetails) {
    this.personalPropertyCoverageAmount = personalPropertyCoverageAmount;
    this.civilLiabilityLimit = civilLiabilityLimit;
    this.currentCoverageDetails = currentCoverageDetails;
    this.currentPremiumDetails = currentPremiumDetails;
  }

  public Amount getPersonalPropertyCoverageAmount() {
    return personalPropertyCoverageAmount;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return currentCoverageDetails;
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return currentPremiumDetails;
  }

  public boolean isFilled() {
    return personalPropertyCoverageAmount != null || civilLiabilityLimit != null;
  }
}
