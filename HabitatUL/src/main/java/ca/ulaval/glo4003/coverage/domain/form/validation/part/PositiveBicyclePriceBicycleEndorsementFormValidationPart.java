package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidationPart;

public class PositiveBicyclePriceBicycleEndorsementFormValidationPart
    extends PositiveBicyclePriceFormValidationPart implements BicycleEndorsementFormValidationPart {

  @Override
  public void validate(BicycleEndorsementForm form) {
    validate(form.getBicycle().getPrice());
  }
}
