package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.shared.application.concurrency.ConcurrentDecorator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class ConcurrentQuoteAppService extends ConcurrentDecorator<QuoteId> {
  // this is an example of what it would look like
  // I need the PR with the logger stuff to merge so I can continue
  private QuoteAppService appService;

  public void purchaseQuote(QuoteId quoteId) {
    lockAndCall(quoteId, () -> appService.purchaseQuote(quoteId));
  }
}
