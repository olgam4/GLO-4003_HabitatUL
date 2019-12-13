package ca.ulaval.glo4003.coverage.helper.form;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;

public class BicycleEndorsementFormGenerator {
  private BicycleEndorsementFormGenerator() {}

  public static BicycleEndorsementForm createBicycleEndorsementForm() {
    return new BicycleEndorsementForm(
        createBicycle(), createCoverageDetails(), createPremiumDetails());
  }
}
