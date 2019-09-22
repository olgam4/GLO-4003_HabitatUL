package ca.ulaval.glo4003.application;

import ca.ulaval.glo4003.application.quote.QuoteAppService;
import ca.ulaval.glo4003.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.domain.*;
import ca.ulaval.glo4003.domain.commons.Premium;
import ca.ulaval.glo4003.domain.quote.Quote;
import ca.ulaval.glo4003.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.domain.quote.QuoteRequest;
import ca.ulaval.glo4003.generator.QuoteGenerator;
import ca.ulaval.glo4003.generator.QuoteRequestDtoGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.QuoteMatcher.matchesQuote;
import static ca.ulaval.glo4003.matcher.QuoteRequestDtoMatcher.getQuoteRequestDtoMockitoMatcher;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Premium A_PREMIUM = new Premium();

  private QuoteAppService subject;

  @Mock private PremiumCalculator premiumCalculator;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  private QuoteRequestDto quoteRequestDto;
  private Quote quote;

  @Before
  public void setUp() {
    quote = QuoteGenerator.create();
    quoteRequestDto = QuoteRequestDtoGenerator.create();

    when(premiumCalculator.computeQuotePremium(any(QuoteRequest.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteRequest.class))).thenReturn(quote);

    subject = new QuoteAppService(premiumCalculator, quoteRepository, quoteFactory);
  }

  @Test
  public void requestingQuote_calculatesItsPrice() {
    subject.requestQuote(quoteRequestDto);

    verify(premiumCalculator)
        .computeQuotePremium(getQuoteRequestDtoMockitoMatcher(quoteRequestDto));
  }

  @Test
  public void requestingQuote_savesIt() {
    subject.requestQuote(quoteRequestDto);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_returnsCorrespondingQuoteDto() {
    QuoteDto observedQuoteDto = subject.requestQuote(quoteRequestDto);

    matchesQuote(quote, observedQuoteDto);
  }
}
