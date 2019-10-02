package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteAppService {
  private QuotePremiumCalculator quotePremiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;

  public QuoteAppService() {
    this(
        ServiceLocator.resolve(QuotePremiumCalculator.class),
        new QuoteFactory(
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class));
  }

  public QuoteAppService(
      QuotePremiumCalculator quotePremiumCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository) {
    this.quotePremiumCalculator = quotePremiumCalculator;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
  }

  public QuoteDto requestQuote(QuoteFormDto quoteFormDto) {
    QuoteForm quoteForm = QuoteAssembler.from(quoteFormDto);
    Premium premium = quotePremiumCalculator.computeQuotePremium(quoteForm);
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
