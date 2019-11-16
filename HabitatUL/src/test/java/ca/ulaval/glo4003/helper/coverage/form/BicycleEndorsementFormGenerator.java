package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;

public class BicycleEndorsementFormGenerator {
  private BicycleEndorsementFormGenerator() {}

  public static BicycleEndorsementForm createBicycleEndorsementForm() {
    return new BicycleEndorsementForm(
        createBicycle(), createCoverageDetails(), createPremiumDetails());
  }
}
