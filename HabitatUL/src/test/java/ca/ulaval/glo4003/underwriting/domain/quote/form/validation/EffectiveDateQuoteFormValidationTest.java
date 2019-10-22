package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteEffectiveDateError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.validation.EffectiveDateQuoteFormValidation.NUMBER_OF_MONTHS_OF_COVERAGE;

public class EffectiveDateQuoteFormValidationTest {
  private static final ClockProvider clockProvider = new FixedClockProvider();
  private static final LocalDate NOW = LocalDate.now(clockProvider.getClock());
  private static final Date VALID_EFFECTIVE_DATE = Date.from(NOW);
  private static final int RANDOM_NUMBER_YEARS = Faker.instance().number().randomDigitNotZero();
  private static final LocalDate PAST_DATE =
      NOW.minus(Period.ofMonths(NUMBER_OF_MONTHS_OF_COVERAGE + RANDOM_NUMBER_YEARS));
  private static final Date INVALID_PAST_EFFECTIVE_DATE = Date.from(PAST_DATE);
  private static final LocalDate FUTURE_DATE =
      NOW.plus(Period.ofMonths(NUMBER_OF_MONTHS_OF_COVERAGE + RANDOM_NUMBER_YEARS));
  private static final Date INVALID_FUTURE_EFFECTIVE_DATE = Date.from(FUTURE_DATE);

  private EffectiveDateQuoteFormValidation subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = new EffectiveDateQuoteFormValidation(clockProvider);
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
