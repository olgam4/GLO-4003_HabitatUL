package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class BicycleEndorsementForm extends ValueObject {
  private final Bicycle bicycle;
  private final CoverageDetails currentCoverageDetails;
  private final PremiumDetails currentPremiumDetails;

  public BicycleEndorsementForm(
      Bicycle bicycle,
      CoverageDetails currentCoverageDetails,
      PremiumDetails currentPremiumDetails) {
    this.bicycle = bicycle;
    this.currentCoverageDetails = currentCoverageDetails;
    this.currentPremiumDetails = currentPremiumDetails;
  }

  public Bicycle getBicycle() {
    return bicycle;
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return currentCoverageDetails;
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return currentPremiumDetails;
  }
}
