package ca.ulaval.glo4003.quote.application.quote;

import ca.ulaval.glo4003.quote.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.quote.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.quote.domain.*;
import ca.ulaval.glo4003.quote.domain.commons.Premium;
import ca.ulaval.glo4003.quote.domain.quote.Quote;
import ca.ulaval.glo4003.quote.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.quote.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.quote.domain.quote.QuoteRequest;

public class QuoteAppService {
  private PremiumCalculator premiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;

  public QuoteAppService(
      PremiumCalculator premiumCalculator,
      QuoteRepository quoteRepository,
      QuoteFactory quoteFactory) {
    this.premiumCalculator = premiumCalculator;
    this.quoteRepository = quoteRepository;
    this.quoteFactory = quoteFactory;
  }

  public QuoteDto requestQuote(QuoteRequestDto quoteRequestDto) {
    QuoteRequest quoteRequest = QuoteAssembler.from(quoteRequestDto);
    Premium premium = premiumCalculator.computeQuotePremium(quoteRequest);
    Quote quote = quoteFactory.create(premium, quoteRequest);
    quoteRepository.create(quote);
    return QuoteAssembler.from(quote);
  }
}
