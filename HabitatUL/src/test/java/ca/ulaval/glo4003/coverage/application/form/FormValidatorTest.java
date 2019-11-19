package ca.ulaval.glo4003.coverage.application.form;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormGenerator.createBicycleEndorsementForm;
import static ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormGenerator.createCoverageModificationForm;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FormValidatorTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final BicycleEndorsementForm BICYCLE_FORM = createBicycleEndorsementForm();
  private static final CoverageModificationForm COVERAGE_MODIFICATION_FORM =
      createCoverageModificationForm();

  @Mock QuoteFormValidation quoteFormValidation;
  @Mock BicycleEndorsementFormValidation bicycleEndorsementFormValidation;
  @Mock CoverageModificationFormValidation coverageModificationFormValidation;

  private FormValidator subject;

  @Before
  public void setUp() {
    subject =
        new FormValidator(
            quoteFormValidation,
            bicycleEndorsementFormValidation,
            coverageModificationFormValidation);
  }

  @Test
  public void validatingQuoteForm_shouldExecuteQuoteFormValidation() {
    subject.validateQuoteForm(QUOTE_FORM);

    verify(quoteFormValidation).validate(QUOTE_FORM);
  }

  @Test
  public void validatingBicycleEndorsementForm_shouldExecuteBicycleEndorsementFormValidation() {
    subject.validateBicycleEndorsementForm(BICYCLE_FORM);

    verify(bicycleEndorsementFormValidation).validate(BICYCLE_FORM);
  }

  @Test
  public void validatingCoverageModificationForm_shouldExecuteCoverageModificationFormValidation() {
    subject.validateCoverageModificationForm(COVERAGE_MODIFICATION_FORM);

    verify(coverageModificationFormValidation).validate(COVERAGE_MODIFICATION_FORM);
  }
}
