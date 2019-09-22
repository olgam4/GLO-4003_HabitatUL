package ca.ulaval.glo4003.quote.application.quote;

import ca.ulaval.glo4003.quote.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.quote.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.quote.domain.PaymentProcessor;
import ca.ulaval.glo4003.quote.domain.PremiumCalculator;
import ca.ulaval.glo4003.quote.domain.commons.Premium;
import ca.ulaval.glo4003.quote.domain.quote.*;

public class QuoteAppService {
  private PremiumCalculator premiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;
  private PaymentProcessor paymentProcessor;

  public QuoteAppService(
      PremiumCalculator premiumCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository,
      PaymentProcessor paymentProcessor) {
    this.premiumCalculator = premiumCalculator;
    this.quoteRepository = quoteRepository;
    this.quoteFactory = quoteFactory;
    this.paymentProcessor = paymentProcessor;
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
    paymentProcessor.processPayment(quote.getPremium());
    quoteRepository.update(quote);
  }
}
