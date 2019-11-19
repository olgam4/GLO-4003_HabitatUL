package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CoverageModificationFormGenerator {
  private CoverageModificationFormGenerator() {}

  public static CoverageModificationForm createCoverageModificationForm() {
    return new CoverageModificationForm(
        createCoverageAmount(),
        createCivilLiabilityLimit(),
        createCoverageDetails(),
        createPremiumDetails());
  }
}
