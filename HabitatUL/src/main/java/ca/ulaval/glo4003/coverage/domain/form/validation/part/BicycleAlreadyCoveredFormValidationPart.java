package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.BicycleAlreadyCoveredError;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.BICYCLE_ENDORSEMENT;

public class BicycleAlreadyCoveredFormValidationPart
    implements BicycleEndorsementFormValidationPart {
  @Override
  public void validate(BicycleEndorsementForm form) {
    if (form.getCurrentCoverageDetails().includes(BICYCLE_ENDORSEMENT)) {
      throw new BicycleAlreadyCoveredError();
    }
  }
}
