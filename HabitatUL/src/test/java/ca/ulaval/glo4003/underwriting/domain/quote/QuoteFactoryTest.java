package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final Premium A_PREMIUM = PremiumGenerator.create();
  private static final Duration VALIDITY_PERIOD = Duration.of(1, ChronoUnit.MINUTES);

  @Mock private QuoteValidityPeriodProvider quoteValidityPeriodProvider;

  private QuoteFactory subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    when(quoteValidityPeriodProvider.getQuoteValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    clockProvider = new FixedClockProvider();
    subject = new QuoteFactory(quoteValidityPeriodProvider, clockProvider);
  }

  @Test
  public void creatingQuote_shouldProperlyComputeExpirationDate() {
    Quote quote = subject.create(A_PREMIUM, QuoteFormGenerator.createValidQuoteForm());

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(clockProvider.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(A_PREMIUM, QuoteFormGenerator.createValidQuoteForm());

    assertFalse(quote.isPurchased());
  }
}
