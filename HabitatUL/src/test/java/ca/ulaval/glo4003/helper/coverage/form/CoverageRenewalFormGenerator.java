package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;

public class CoverageRenewalFormGenerator {
  private CoverageRenewalFormGenerator() {}

  public static CoverageRenewalForm createCoverageRenewalForm() {
    return new CoverageRenewalForm(
        createCoverageAmount(), createCoverageDetails(), createPremiumDetails());
  }
}
