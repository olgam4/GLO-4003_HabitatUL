package ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationTest;

import static ca.ulaval.glo4003.coverage.helper.form.CoverageModificationFormGenerator.createCoverageModificationForm;

public class CoverageModificationFormValidationTest
    extends FormValidationTest<CoverageModificationForm> {
  @Override
  public FormValidation createSubject() {
    return new CoverageModificationFormValidation();
  }

  @Override
  public CoverageModificationForm createForm() {
    return createCoverageModificationForm();
  }
}
