package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuoteTest {
  private Quote subject;

  @Before
  public void setUp() {
    subject = QuoteGenerator.createValidQuote();
  }

  @Test
  public void creatingQuote_shouldComputeEffectivePeriod() {
    Date startDate = Date.from(LocalDate.now());
    subject = QuoteGenerator.createValidQuoteWithEffectiveDate(startDate);
    Date endDate = startDate.plus(java.time.Period.ofMonths(Quote.COVERAGE_PERIOD_IN_MONTHS));

    Period expected = new Period(startDate, endDate);
    assertEquals(expected, subject.getEffectivePeriod());
  }

  @Test
  public void purchasingQuote_shouldMarkQuoteAsPurchased() {
    subject.purchase();

    assertTrue(subject.isPurchased());
  }

  @Test(expected = QuoteExpiredError.class)
  public void purchasingQuote_withExpiredQuote_shouldThrow() {
    subject = QuoteGenerator.createExpiredQuote();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedError.class)
  public void purchasingQuote_withAlreadyPurchasedQuote_shouldThrow() {
    subject = QuoteGenerator.createPurchasedQuote();

    subject.purchase();
  }

  @Test
  public void purchasingQuote_shouldRegisterQuotePurchasedEvent() {
    subject.purchase();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(QuotePurchasedEvent.class, events.get(0).getClass());
  }
}
