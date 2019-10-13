package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.junit.Before;
import org.junit.Test;

public class QuoteFormValidatorTest {
  private static final ClockProvider clockProvider = new FixedClockProvider();

  private QuoteFormValidator subject;

  @Before
  public void setUp() {
    subject = new QuoteFormValidator(clockProvider);
  }

  @Test
  public void validatingQuoteForm_withValidQuoteForm_shouldNotThrow() {
    subject.validate(QuoteFormGenerator.createQuoteForm());
  }
}
