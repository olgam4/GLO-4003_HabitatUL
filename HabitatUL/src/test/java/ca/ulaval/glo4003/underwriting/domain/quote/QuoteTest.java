package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.exception.ExpiredQuoteException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.generator.QuoteGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuoteTest {
  private Quote subject;

  @Before
  public void setUp() {}

  @Test
  public void purchasingQuote_shouldMarkQuoteAsPurchased() {
    subject = QuoteGenerator.createValidQuote();

    subject.purchase();

    assertTrue(subject.isPurchased());
  }

  @Test(expected = ExpiredQuoteException.class)
  public void purchasingQuote_withExpiredQuote_shouldThrow() {
    subject = QuoteGenerator.createExpiredQuote();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedException.class)
  public void purchasingQuote_withAlreadyPurchasedQuote_shouldThrow() {
    subject = QuoteGenerator.createPurchasedQuote();

    subject.purchase();
  }
}
