package ca.ulaval.glo4003.coverage.helper.form;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;

public class CoverageRenewalFormGenerator {
  private CoverageRenewalFormGenerator() {}

  public static CoverageRenewalForm createCoverageRenewalForm() {
    return new CoverageRenewalForm(
        createCoverageAmount(), createCoverageDetails(), createPremiumDetails());
  }
}
