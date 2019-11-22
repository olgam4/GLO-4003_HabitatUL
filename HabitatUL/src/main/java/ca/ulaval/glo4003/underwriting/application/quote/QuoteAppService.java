package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public interface QuoteAppService {
  QuoteDto requestQuote(RequestQuoteDto requestQuoteDto);

  void purchaseQuote(QuoteId quoteId);
}
