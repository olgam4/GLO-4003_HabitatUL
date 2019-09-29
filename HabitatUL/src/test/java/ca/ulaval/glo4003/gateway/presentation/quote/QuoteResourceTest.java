package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequest;
import ca.ulaval.glo4003.generator.QuoteFormGenerator;
import ca.ulaval.glo4003.generator.QuoteGenerator;
import ca.ulaval.glo4003.matcher.QuoteFormDtoMatcher;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceTest {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private QuoteAppService quoteAppService;

  private QuoteResource subject;
  private QuoteRequest quoteRequest;
  private QuoteViewAssembler quoteViewAssembler;
  private QuoteDto quoteDto;

  @Before
  public void setUp() {
    quoteRequest = QuoteFormGenerator.createQuoteRequest();
    quoteViewAssembler = new QuoteViewAssembler();
    quoteDto = QuoteGenerator.createValidQuoteDto();
    subject = new QuoteResource(quoteAppService, quoteViewAssembler);
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
  }

  @Test
  public void requestingQuote_shouldCallQuoteAppService() {
    subject.requestQuote(quoteRequest);

    verify(quoteAppService)
        .requestQuote(QuoteFormDtoMatcher.getQuoteFormDtoMockitoMatcher(quoteRequest));
  }

  @Test
  public void purchasingQuote_shouldCallQuoteAppService() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteAppService).purchaseQuote(QUOTE_ID);
  }
}
