package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteExpiredException;
import org.junit.Before;
import org.junit.Test;

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
  public void purchasingQuote_shouldMarkQuoteAsPurchased() {
    subject.purchase();

    assertTrue(subject.isPurchased());
  }

  @Test(expected = QuoteExpiredException.class)
  public void purchasingQuote_withExpiredQuote_shouldThrow() {
    subject = QuoteGenerator.createExpiredQuote();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedException.class)
  public void purchasingQuote_withAlreadyPurchasedQuote_shouldThrow() {
    subject = QuoteGenerator.createPurchasedQuote();

    subject.purchase();
  }

  @Test
  public void purchasingQuote_withoutEvent_shouldRegisterQuotePurchasedEvent() {
    subject.purchase();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals("quotePurchasedEvent", events.get(0).getType());
  }
}
