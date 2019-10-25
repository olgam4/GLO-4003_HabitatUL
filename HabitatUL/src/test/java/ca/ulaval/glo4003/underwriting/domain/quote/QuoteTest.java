package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.helper.quote.QuoteBuilder;
import ca.ulaval.glo4003.helper.quote.QuoteGenerator;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuoteTest {
  private Quote subject;

  @Test
  public void purchasingQuote_shouldMarkQuoteAsPurchased() {
    subject = QuoteBuilder.aQuote().unPurchased().build();

    subject.purchase();

    assertTrue(subject.isPurchased());
  }

  @Test(expected = QuoteExpiredError.class)
  public void purchasingQuote_withExpiredQuote_shouldThrow() {
    subject = QuoteBuilder.aQuote().expired().build();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedError.class)
  public void purchasingQuote_withAlreadyPurchasedQuote_shouldThrow() {
    subject = QuoteBuilder.aQuote().purchased().build();

    subject.purchase();
  }

  @Test
  public void purchasingQuote_shouldRegisterQuotePurchasedEvent() {
    subject = QuoteGenerator.createQuote();
    subject.purchase();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(QuotePurchasedEvent.class, events.get(0).getClass());
  }
}
