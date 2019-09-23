package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.*;

public class QuoteAppService {
  private PremiumCalculator premiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;

  public QuoteAppService(
      PremiumCalculator premiumCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository) {
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

  public void purchaseQuote(QuoteId quoteId) {
    Quote quote = quoteRepository.getById(quoteId);
    quote.purchase();
    quoteRepository.update(quote);
  }
}
