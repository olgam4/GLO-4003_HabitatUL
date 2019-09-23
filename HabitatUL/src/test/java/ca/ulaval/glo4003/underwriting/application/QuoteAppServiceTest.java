package ca.ulaval.glo4003.underwriting.application;

import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.generator.QuoteRequestGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.matcher.QuoteMatcher.matchesQuote;
import static ca.ulaval.glo4003.underwriting.matcher.QuoteRequestDtoMatcher.getQuoteRequestDtoMockitoMatcher;
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

  private QuoteRequestDto quoteRequestDto;

  @Before
  public void setUp() {
    quoteRequestDto = QuoteRequestGenerator.createDto();

    when(premiumCalculator.computeQuotePremium(any(QuoteRequest.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteRequest.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject = new QuoteAppService(premiumCalculator, quoteFactory, quoteRepository);
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
  public void purchasingQuote_updatesQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).update(quote);
  }
}
