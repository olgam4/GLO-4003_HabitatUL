package ca.ulaval.glo4003.quote.application;

import ca.ulaval.glo4003.quote.application.quote.QuoteAppService;
import ca.ulaval.glo4003.quote.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.quote.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.quote.domain.PaymentProcessor;
import ca.ulaval.glo4003.quote.domain.PremiumCalculator;
import ca.ulaval.glo4003.quote.domain.commons.Premium;
import ca.ulaval.glo4003.quote.domain.quote.*;
import ca.ulaval.glo4003.quote.generator.QuoteRequestDtoGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.quote.matcher.QuoteMatcher.matchesQuote;
import static ca.ulaval.glo4003.quote.matcher.QuoteRequestDtoMatcher.getQuoteRequestDtoMockitoMatcher;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Premium A_PREMIUM = new Premium();
  private static final QuoteId QUOTE_ID = new QuoteId();

  private QuoteAppService subject;

  @Mock private PremiumCalculator premiumCalculator;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;
  @Mock private PaymentProcessor paymentProcessor;

  private QuoteRequestDto quoteRequestDto;

  @Before
  public void setUp() {
    quoteRequestDto = QuoteRequestDtoGenerator.create();

    when(premiumCalculator.computeQuotePremium(any(QuoteRequest.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteRequest.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);
    when(quote.getPremium()).thenReturn(A_PREMIUM);

    subject =
        new QuoteAppService(premiumCalculator, quoteFactory, quoteRepository, paymentProcessor);
  }

  @Test
  public void requestingQuote_computesQuotePrice() {
    subject.requestQuote(quoteRequestDto);

    verify(premiumCalculator)
        .computeQuotePremium(getQuoteRequestDtoMockitoMatcher(quoteRequestDto));
  }

  @Test
  public void requestingQuote_createsQuote() {
    subject.requestQuote(quoteRequestDto);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_returnsCorrespondingQuoteDto() {
    QuoteDto observedQuoteDto = subject.requestQuote(quoteRequestDto);

    matchesQuote(quote, observedQuoteDto);
  }

  @Test
  public void purchasingQuote_getsQuoteById() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).getById(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_purchasesQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quote).purchase();
  }

  @Test
  public void purchasingQuote_processesPayment() {
    subject.purchaseQuote(QUOTE_ID);

    verify(paymentProcessor).processPayment(A_PREMIUM);
  }

  @Test
  public void purchasingQuote_updatesQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).update(quote);
  }
}
