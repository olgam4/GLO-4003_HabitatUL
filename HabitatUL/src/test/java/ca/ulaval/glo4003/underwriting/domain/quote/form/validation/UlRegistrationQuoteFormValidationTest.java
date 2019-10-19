package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentInformationError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UlRegistrationQuoteFormValidationTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private UlRegistrarOffice ulRegistrarOffice;

  private UlRegistrationQuoteFormValidation subject;

  @Before
  public void setUp() {
    subject = new UlRegistrationQuoteFormValidation(ulRegistrarOffice);
  }

  @Test
  public void validatingUlRegistration_withValidStudentInformation_shouldNotThrow() {
    when(ulRegistrarOffice.isValidRegistration(
            any(String.class), any(String.class), any(String.class)))
        .thenReturn(true);

    subject.validate(QUOTE_FORM);
  }

  @Test(expected = QuoteStudentInformationError.class)
  public void validatingUlRegistration_withInvalidStudentInformation_shouldThrow() {
    when(ulRegistrarOffice.isValidRegistration(
            any(String.class), any(String.class), any(String.class)))
        .thenReturn(false);

    subject.validate(QUOTE_FORM);
  }
}
