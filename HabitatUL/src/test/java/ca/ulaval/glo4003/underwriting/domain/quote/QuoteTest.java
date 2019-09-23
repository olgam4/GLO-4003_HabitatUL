package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.exception.ExpiredQuoteException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.generator.QuoteGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuoteTest {
  private Quote subject;

  @Before
  public void setUp() {}

  @Test
  public void purchasingQuote_marksQuoteAsPurchased() {
    subject = QuoteGenerator.createValidQuote();

    subject.purchase();

    assertTrue(subject.isPurchased());
  }

  @Test(expected = ExpiredQuoteException.class)
  public void purchasingQuote_whenQuoteIsExpired_throws() {
    subject = QuoteGenerator.createExpiredQuote();

    subject.purchase();
  }

  @Test(expected = QuoteAlreadyPurchasedException.class)
  public void purchasingQuote_whenQuoteIsAlreadyPurchased_throws() {
    subject = QuoteGenerator.createPurchasedQuote();

    subject.purchase();
  }
}
