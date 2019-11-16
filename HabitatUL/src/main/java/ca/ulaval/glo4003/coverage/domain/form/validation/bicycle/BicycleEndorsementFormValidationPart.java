package ca.ulaval.glo4003.coverage.domain.form.validation.bicycle;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationPart;

public interface BicycleEndorsementFormValidationPart
    extends FormValidationPart<BicycleEndorsementForm> {
  void validate(BicycleEndorsementForm bicycleEndorsementForm);
}
