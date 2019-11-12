package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;

import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final Duration VALIDITY_PERIOD = TemporalGenerator.createDuration();
  private static final java.time.Period COVERAGE_PERIOD = TemporalGenerator.createJavaTimePeriod();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final CoverageDetails COVERAGE_DETAILS =
      CoverageDetailsGenerator.createCoverageDetails();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();

  @Mock private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  @Mock private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;

  private QuoteFactory subject;

  @Before
  public void setUp() {
    when(quoteValidityPeriodProvider.getQuoteValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    when(quoteEffectivePeriodProvider.getQuoteEffectivePeriod()).thenReturn(COVERAGE_PERIOD);
    subject =
        new QuoteFactory(quoteValidityPeriodProvider, quoteEffectivePeriodProvider, CLOCK_PROVIDER);
  }

  @Test
  public void creatingQuote_shouldComputeExpirationDate() {
    Quote quote = subject.create(QUOTE_FORM, COVERAGE_DETAILS, PREMIUM_DETAILS);

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldComputeEffectivePeriod() {
    Quote quote = subject.create(QUOTE_FORM, COVERAGE_DETAILS, PREMIUM_DETAILS);

    Period expectedEffectivePeriod =
        new Period(
            QUOTE_FORM.getEffectiveDate(), QUOTE_FORM.getEffectiveDate().plus(COVERAGE_PERIOD));
    assertEquals(expectedEffectivePeriod, quote.getEffectivePeriod());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(QUOTE_FORM, COVERAGE_DETAILS, PREMIUM_DETAILS);

    assertFalse(quote.isPurchased());
  }
}
