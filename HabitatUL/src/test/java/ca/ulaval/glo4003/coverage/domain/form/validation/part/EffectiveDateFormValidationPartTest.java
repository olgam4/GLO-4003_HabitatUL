package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.error.QuoteEffectiveDateError;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Period;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EffectiveDateFormValidationPartTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final Date VALID_EFFECTIVE_DATE =
      Date.from(LocalDate.now(CLOCK_PROVIDER.getClock()));
  private static final Period EFFECTIVE_PERIOD = TemporalGenerator.createJavaTimePeriod();
  private static final Date INVALID_PAST_EFFECTIVE_DATE =
      TemporalGenerator.createDateBefore(VALID_EFFECTIVE_DATE);
  private static final Date INVALID_FUTURE_EFFECTIVE_DATE =
      TemporalGenerator.createDateAfter(VALID_EFFECTIVE_DATE.plus(EFFECTIVE_PERIOD));

  @Mock private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;

  private EffectiveDateFormValidationPart subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    when(quoteEffectivePeriodProvider.getQuoteEffectivePeriod()).thenReturn(EFFECTIVE_PERIOD);
    subject = new EffectiveDateFormValidationPart(quoteEffectivePeriodProvider, CLOCK_PROVIDER);
  }

  @Test
  public void validatingQuoteForm_withValidEffectiveDate_shouldNotThrow() {
    quoteForm = QuoteFormBuilder.aQuoteForm().withEffectiveDate(VALID_EFFECTIVE_DATE).build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuoteEffectiveDateError.class)
  public void validatingQuoteForm_withInvalidPastEffectiveDate_shouldThrow() {
    quoteForm =
        QuoteFormBuilder.aQuoteForm().withEffectiveDate(INVALID_PAST_EFFECTIVE_DATE).build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuoteEffectiveDateError.class)
  public void validatingQuoteForm_withInvalidFutureEffectiveDate_shouldThrow() {
    quoteForm =
        QuoteFormBuilder.aQuoteForm().withEffectiveDate(INVALID_FUTURE_EFFECTIVE_DATE).build();

    subject.validate(quoteForm);
  }
}
