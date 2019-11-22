package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createNumberOfUnits;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CoverageModificationFormBuilder {
  private final Amount DEFAULT_COVERAGE_AMOUNT = createCoverageAmount();
  private final CivilLiabilityLimit DEFAULT_CIVIL_LIABILITY_LIMIT = createCivilLiabilityLimit();
  private final int DEFAULT_NUMBER_OF_UNITS = createNumberOfUnits();
  private final CoverageDetails DEFAULT_CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_CURRENT_PREMIUM_DETAILS = createPremiumDetails();

  private Amount coverageAmount = DEFAULT_COVERAGE_AMOUNT;
  private CivilLiabilityLimit civilLiabilityLimit = DEFAULT_CIVIL_LIABILITY_LIMIT;
  private CoverageDetails currentCoverageDetails = DEFAULT_CURRENT_COVERAGE_DETAILS;
  private PremiumDetails currentPremiumDetails = DEFAULT_CURRENT_PREMIUM_DETAILS;
  private int numberOfUnits = DEFAULT_NUMBER_OF_UNITS;

  private CoverageModificationFormBuilder() {}

  public static CoverageModificationFormBuilder aCoverageModificationForm() {
    return new CoverageModificationFormBuilder();
  }

  public CoverageModificationFormBuilder withCoverageAmount(Amount coverageAmount) {
    this.coverageAmount = coverageAmount;
    return this;
  }

  public CoverageModificationFormBuilder withCivilLiabilityLimit(
      CivilLiabilityLimit civilLiabilityLimit) {
    this.civilLiabilityLimit = civilLiabilityLimit;
    return this;
  }

  public CoverageModificationFormBuilder withNumberOfUnits(int numberOfUnits) {
    this.numberOfUnits = numberOfUnits;
    return this;
  }

  public CoverageModificationFormBuilder withCurrentCoverageDetails(
      CoverageDetails currentCoverageDetails) {
    this.currentCoverageDetails = currentCoverageDetails;
    return this;
  }

  public CoverageModificationFormBuilder withCurrentPremiumDetails(
      PremiumDetails currentPremiumDetails) {
    this.currentPremiumDetails = currentPremiumDetails;
    return this;
  }

  public CoverageModificationForm build() {
    return new CoverageModificationForm(
        coverageAmount,
        civilLiabilityLimit,
        numberOfUnits,
        currentCoverageDetails,
        currentPremiumDetails);
  }
}
