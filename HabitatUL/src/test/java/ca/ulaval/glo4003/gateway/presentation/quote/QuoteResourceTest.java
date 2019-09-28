package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.generator.QuoteGenerator;
import ca.ulaval.glo4003.generator.QuoteRequestGenerator;
import ca.ulaval.glo4003.matcher.QuoteRequestDtoMatcher;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceTest {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private QuoteAppService quoteAppService;

  private QuoteResource subject;
  private QuoteRequestView quoteRequestView;
  private QuoteViewAssembler quoteViewAssembler;
  private QuoteDto quoteDto;

  @Before
  public void setUp() {
    quoteRequestView = QuoteRequestGenerator.createQuoteRequestView();
    quoteViewAssembler = new QuoteViewAssembler();
    quoteDto = QuoteGenerator.createValidQuoteDto();
    subject = new QuoteResource(quoteAppService, quoteViewAssembler);
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
  }

  @Test
  public void requestingQuote_shouldCallQuoteAppService() {
    subject.requestQuote(quoteRequestView);

    verify(quoteAppService)
        .requestQuote(QuoteRequestDtoMatcher.getQuoteRequestDtoMockitoMatcher(quoteRequestView));
  }

  @Test
  public void purchasingQuote_shouldCallQuoteAppService() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteAppService).purchaseQuote(QUOTE_ID);
  }
}
