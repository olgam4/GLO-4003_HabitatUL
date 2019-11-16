package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.coverage.domain.form.validation.bicycle.BicycleEndorsementFormValidation;

public class BicycleEndorsementFormValidationAssembler {
  public static BicycleEndorsementFormValidation assemble() {
    BicycleEndorsementFormValidation bicycleEndorsementFormValidation =
        new BicycleEndorsementFormValidation();
    return bicycleEndorsementFormValidation;
  }
}
