package ca.ulaval.glo4003.coverage.helper.form;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createNumberOfUnits;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CoverageModificationFormGenerator {
  private CoverageModificationFormGenerator() {}

  public static CoverageModificationForm createCoverageModificationForm() {
    return new CoverageModificationForm(
        createCoverageAmount(),
        createCivilLiabilityLimit(),
        createNumberOfUnits(),
        createCoverageDetails(),
        createPremiumDetails());
  }
}
