package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.*;

public class QuoteAppService {
  private PremiumCalculator premiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;

  public QuoteAppService() {
    this(
        ServiceLocator.resolve(PremiumCalculator.class),
        new QuoteFactory(
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class));
  }

  public QuoteAppService(
      PremiumCalculator premiumCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository) {
    this.premiumCalculator = premiumCalculator;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
  }

  public QuoteDto requestQuote(QuoteFormDto quoteFormDto) {
    QuoteForm quoteForm = QuoteAssembler.from(quoteFormDto);
    Premium premium = premiumCalculator.computeQuotePremium(quoteForm);
    Quote quote = quoteFactory.create(premium, quoteForm);
    quoteRepository.create(quote);
    return QuoteAssembler.from(quote);
  }

  public void purchaseQuote(QuoteId quoteId) {
    Quote quote = quoteRepository.getById(quoteId);
    quote.purchase();
    quoteRepository.update(quote);
  }
}
