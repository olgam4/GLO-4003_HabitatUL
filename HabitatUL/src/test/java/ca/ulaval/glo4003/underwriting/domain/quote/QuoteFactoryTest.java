package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final Money A_PRICE = MoneyGenerator.create();
  private static final Duration VALIDITY_PERIOD = Duration.of(1, ChronoUnit.MINUTES);
  private static final java.time.Period COVERAGE_PERIOD = Period.ofYears(1);

  @Mock private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  @Mock private EffectivePeriodProvider effectivePeriodProvider;

  private QuoteFactory subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    when(quoteValidityPeriodProvider.getQuoteValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    when(effectivePeriodProvider.getEffectivePeriod()).thenReturn(COVERAGE_PERIOD);
    clockProvider = new FixedClockProvider();
    subject = new QuoteFactory(effectivePeriodProvider, quoteValidityPeriodProvider, clockProvider);
  }

  @Test
  public void creatingQuote_shouldProperlyComputeExpirationDate() {
    Quote quote = subject.create(A_PRICE, QuoteFormGenerator.createQuoteForm());

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(clockProvider.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(A_PRICE, QuoteFormGenerator.createQuoteForm());

    assertFalse(quote.isPurchased());
  }
}
