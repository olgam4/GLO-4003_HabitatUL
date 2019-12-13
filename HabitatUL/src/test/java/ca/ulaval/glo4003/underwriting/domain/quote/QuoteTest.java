package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ca.ulaval.glo4003.underwriting.domain.quote.QuoteStatus.*;
import static org.junit.Assert.assertEquals;

public class QuoteTest {
  private Quote subject;

  @Before
  public void setUp() {
    subject = QuoteBuilder.aQuote().withStatus(CREATED).build();
  }

  @Test
  public void purchasingQuote_shouldMarkQuoteAsPurchased() {
    subject.purchase();

    assertEquals(PURCHASED, subject.getStatus());
  }

  @Test(expected = QuoteExpiredError.class)
  public void purchasingQuote_withExpiredQuote_shouldThrow() {
    subject = QuoteBuilder.aQuote().withStatus(EXPIRED).build();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedError.class)
  public void purchasingQuote_withAlreadyPurchasedQuote_shouldThrow() {
    subject = QuoteBuilder.aQuote().withStatus(PURCHASED).build();

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
