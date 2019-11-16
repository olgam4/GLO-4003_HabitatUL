package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;

public class BicycleEndorsementFormBuilder {
  private static final Bicycle DEFAULT_BICYCLE = createBicycle();
  private static final CoverageDetails DEFAULT_CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails DEFAULT_CURRENT_PREMIUM_DETAILS = createPremiumDetails();

  private Bicycle bicycle = DEFAULT_BICYCLE;
  private CoverageDetails currentCoverageDetails = DEFAULT_CURRENT_COVERAGE_DETAILS;
  private PremiumDetails currentPremiumDetails = DEFAULT_CURRENT_PREMIUM_DETAILS;

  private BicycleEndorsementFormBuilder() {}

  public static BicycleEndorsementFormBuilder aBicycleEndorsementForm() {
    return new BicycleEndorsementFormBuilder();
  }

  public BicycleEndorsementFormBuilder withBicycle(Bicycle bicycle) {
    this.bicycle = bicycle;
    return this;
  }

  public BicycleEndorsementFormBuilder withCurrentCoverageDetails(
      CoverageDetails currentCoverageDetails) {
    this.currentCoverageDetails = currentCoverageDetails;
    return this;
  }

  public BicycleEndorsementForm build() {
    return new BicycleEndorsementForm(bicycle, currentCoverageDetails, currentPremiumDetails);
  }
}
