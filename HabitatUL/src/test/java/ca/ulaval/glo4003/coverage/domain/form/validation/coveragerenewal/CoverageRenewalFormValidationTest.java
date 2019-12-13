package ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationTest;

import static ca.ulaval.glo4003.coverage.helper.form.CoverageRenewalFormGenerator.createCoverageRenewalForm;

public class CoverageRenewalFormValidationTest extends FormValidationTest<CoverageRenewalForm> {
  @Override
  public FormValidation createSubject() {
    return new CoverageRenewalFormValidation();
  }

  @Override
  public CoverageRenewalForm createForm() {
    return createCoverageRenewalForm();
  }
}
