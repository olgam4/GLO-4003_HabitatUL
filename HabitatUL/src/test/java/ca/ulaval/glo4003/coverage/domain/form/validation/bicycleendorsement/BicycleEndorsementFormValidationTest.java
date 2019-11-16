package ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationTest;

import static ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormGenerator.createBicycleEndorsementForm;

public class BicycleEndorsementFormValidationTest extends FormValidationTest {
  @Override
  public FormValidation createSubject() {
    return new BicycleEndorsementFormValidation();
  }

  @Override
  public BicycleEndorsementForm createForm() {
    return createBicycleEndorsementForm();
  }
}
