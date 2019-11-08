package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final Money PREMIUM = MoneyGenerator.createMoney();
  private static final Duration VALIDITY_PERIOD = TemporalGenerator.createDuration();
  private static final java.time.Period COVERAGE_PERIOD = TemporalGenerator.createJavaTimePeriod();

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
  public void creatingQuote_shouldProperlyComputeExpirationDate() {
    Quote quote = subject.create(PREMIUM, QuoteFormGenerator.createQuoteForm());

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(PREMIUM, QuoteFormGenerator.createQuoteForm());

    assertFalse(quote.isPurchased());
  }
}
