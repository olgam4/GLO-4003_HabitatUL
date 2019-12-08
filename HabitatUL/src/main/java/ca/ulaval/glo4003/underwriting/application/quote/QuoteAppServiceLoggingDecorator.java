package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAppServiceLoggingDecorator implements QuoteAppService {
  private QuoteAppService quoteAppService;
  private Logger logger;

  public QuoteAppServiceLoggingDecorator(QuoteAppService quoteAppService) {
    this(quoteAppService, ServiceLocator.resolve(Logger.class));
  }

  public QuoteAppServiceLoggingDecorator(QuoteAppService quoteAppService, Logger logger) {
    this.logger = logger;
    this.quoteAppService = quoteAppService;
  }

  @Override
  public QuoteDto requestQuote(RequestQuoteDto requestQuoteDto) {
    logger.info(String.format("Requesting quote for <%s>", requestQuoteDto));
    QuoteDto quoteDto = this.quoteAppService.requestQuote(requestQuoteDto);
    logger.info(String.format("Requested quote <%s>", quoteDto));
    return quoteDto;
  }

  @Override
  public void purchaseQuote(QuoteId quoteId) {
    logger.info(String.format("Purchasing quote <%s>", quoteId));
    this.quoteAppService.purchaseQuote(quoteId);
  }
}
