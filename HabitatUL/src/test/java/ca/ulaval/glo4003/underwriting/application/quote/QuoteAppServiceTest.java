package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.generator.QuoteRequestGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.QuoteDtoMatcher.matchesQuote;
import static ca.ulaval.glo4003.matcher.QuoteRequestMatcher.getQuoteRequestDtoMockitoMatcher;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Premium A_PREMIUM = new Premium();
  private static final QuoteId QUOTE_ID = new QuoteId();

  private QuoteAppService subject;
  private QuoteRequestDto quoteRequestDto;

  @Mock private PremiumCalculator premiumCalculator;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  @Before
  public void setUp() {
    quoteRequestDto = QuoteRequestGenerator.createQuoteRequestDto();

    when(premiumCalculator.computeQuotePremium(any(QuoteRequest.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteRequest.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject = new QuoteAppService(premiumCalculator, quoteFactory, quoteRepository);
  }

  @Test
  public void requestingQuote_shouldComputeQuotePrice() {
    subject.requestQuote(quoteRequestDto);

    verify(premiumCalculator)
        .computeQuotePremium(getQuoteRequestDtoMockitoMatcher(quoteRequestDto));
  }

  @Test
  public void requestingQuote_shouldCreateQuote() {
    subject.requestQuote(quoteRequestDto);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_shouldProduceCorrespondingQuoteDto() {
    QuoteDto observedQuoteDto = subject.requestQuote(quoteRequestDto);

    matchesQuote(quote, observedQuoteDto);
  }

  @Test
  public void purchasingQuote_shouldGetQuoteById() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).getById(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_shouldPurchaseQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quote).purchase();
  }

  @Test
  public void purchasingQuote_shouldUpdateQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).update(quote);
  }
}
