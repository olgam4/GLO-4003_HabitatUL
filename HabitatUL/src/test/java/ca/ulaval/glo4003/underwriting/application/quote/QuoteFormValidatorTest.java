package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.QuoteFormValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFormValidatorTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private QuoteFormValidation quoteFormValidation;
  @Mock private QuoteFormValidation anotherQuoteFormValidation;

  private QuoteFormValidator subject;

  @Before
  public void setUp() {
    subject =
        new QuoteFormValidator(Arrays.asList(quoteFormValidation, anotherQuoteFormValidation));
  }

  @Test
  public void computingQuotePrice_shouldConsiderAllValidations() {
    subject.validate(QUOTE_FORM);

    verify(quoteFormValidation).validate(QUOTE_FORM);
    verify(anotherQuoteFormValidation).validate(QUOTE_FORM);
  }
}
