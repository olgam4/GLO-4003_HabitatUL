package ca.ulaval.glo4003.coverage.application.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FormValidatorTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock QuoteFormValidation quoteFormValidation;

  private FormValidator subject;

  @Before
  public void setUp() {
    subject = new FormValidator(quoteFormValidation);
  }

  @Test
  public void validatingQuoteForm_shouldExecuteQuoteFormValidation() {
    subject.validateQuoteForm(QUOTE_FORM);

    verify(quoteFormValidation).validate(QUOTE_FORM);
  }
}
