package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.shared.application.concurrency.ConcurrentDecorator;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAppServiceConcurrentDecorator extends ConcurrentDecorator<QuoteId>
    implements QuoteAppService {
  private QuoteAppService quoteAppService;

  public QuoteAppServiceConcurrentDecorator(QuoteAppService quoteAppService) {
    this.quoteAppService = quoteAppService;
  }

  @Override
  public QuoteDto requestQuote(RequestQuoteDto requestQuoteDto) {
    return quoteAppService.requestQuote(requestQuoteDto);
  }

  @Override
  public void purchaseQuote(QuoteId quoteId) {
    lockAndCall(quoteId, () -> quoteAppService.purchaseQuote(quoteId));
  }
}
