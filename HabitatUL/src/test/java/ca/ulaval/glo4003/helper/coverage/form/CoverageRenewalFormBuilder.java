package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;

public class CoverageRenewalFormBuilder {
  private final Amount DEFAULT_COVERAGE_AMOUNT = createCoverageAmount();
  private final CoverageDetails DEFAULT_CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_CURRENT_PREMIUM_DETAILS = createPremiumDetails();

  private Amount coverageAmount = DEFAULT_COVERAGE_AMOUNT;
  private CoverageDetails currentCoverageDetails = DEFAULT_CURRENT_COVERAGE_DETAILS;
  private PremiumDetails currentPremiumDetails = DEFAULT_CURRENT_PREMIUM_DETAILS;

  private CoverageRenewalFormBuilder() {}

  public static CoverageRenewalFormBuilder aCoverageRenewalForm() {
    return new CoverageRenewalFormBuilder();
  }

  public CoverageRenewalFormBuilder withCoverageAmount(Amount coverageAmount) {
    this.coverageAmount = coverageAmount;
    return this;
  }

  public CoverageRenewalFormBuilder withCurrentCoverageDetails(
      CoverageDetails currentCoverageDetails) {
    this.currentCoverageDetails = currentCoverageDetails;
    return this;
  }

  public CoverageRenewalFormBuilder withCurrentPremiumDetails(
      PremiumDetails currentPremiumDetails) {
    this.currentPremiumDetails = currentPremiumDetails;
    return this;
  }

  public CoverageRenewalForm build() {
    return new CoverageRenewalForm(coverageAmount, currentCoverageDetails, currentPremiumDetails);
  }
}
