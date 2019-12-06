package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.helper.quote.QuoteGenerator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceLoggingDecoratorTest {
  private static final QuoteId QUOTE_ID = QuoteGenerator.createQuoteId();
  private static final RequestQuoteDto QUOTE_FORM_DTO = QuoteGenerator.createRequestQuoteDto();

  @Mock private QuoteAppService quoteAppService;
  @Mock private Logger logger;

  private QuoteAppService subject;

  @Before
  public void setUp() {
    subject = new QuoteAppServiceLoggingDecorator(quoteAppService, logger);
  }

  @Test
  public void requestingQuote_shouldLogParamsAndReturnAsInfo() {
    subject.requestQuote(QUOTE_FORM_DTO);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void requestingQuote_shouldDelegateToQuoteAppService() {
    subject.requestQuote(QUOTE_FORM_DTO);

    verify(quoteAppService).requestQuote(QUOTE_FORM_DTO);
  }

  @Test
  public void purchasingQuote_shouldLogParamsAsInfo() {
    subject.purchaseQuote(QUOTE_ID);

    verify(logger).info(anyString());
  }

  @Test
  public void purchasingQuote_shouldDelegateToQuoteAppService() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteAppService).purchaseQuote(QUOTE_ID);
  }
}
