package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.BicycleAlreadyCoveredFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.PositiveBicyclePriceBicycleEndorsementFormValidationPart;

public class BicycleEndorsementFormValidationAssembler {
  private BicycleEndorsementFormValidationAssembler() {}

  public static BicycleEndorsementFormValidation assemble() {
    BicycleEndorsementFormValidation formValidation = new BicycleEndorsementFormValidation();
    formValidation.addFormValidationPart(new BicycleAlreadyCoveredFormValidationPart());
    formValidation.addFormValidationPart(
        new PositiveBicyclePriceBicycleEndorsementFormValidationPart());
    return formValidation;
  }
}
