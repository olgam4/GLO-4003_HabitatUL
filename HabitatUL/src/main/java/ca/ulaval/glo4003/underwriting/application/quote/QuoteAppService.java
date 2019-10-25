package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteAppService {
  private QuoteAssembler quoteAssembler;
  private QuoteFormValidator quoteFormValidator;
  private QuotePriceCalculator quotePriceCalculator;
  private QuoteFactory quoteFactory;
  private QuoteRepository quoteRepository;

  public QuoteAppService() {
    this(
        new QuoteAssembler(),
        new QuoteFormValidator(),
        new QuotePriceCalculator(),
        new QuoteFactory(
            ServiceLocator.resolve(EffectivePeriodProvider.class),
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class));
  }

  public QuoteAppService(
      QuoteAssembler quoteAssembler,
      QuoteFormValidator quoteFormValidator,
      QuotePriceCalculator quotePriceCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository) {
    this.quoteFormValidator = quoteFormValidator;
    this.quoteAssembler = quoteAssembler;
    this.quotePriceCalculator = quotePriceCalculator;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
  }

  public QuoteDto requestQuote(QuoteFormDto quoteFormDto) {
    try {
      QuoteForm quoteForm = quoteAssembler.from(quoteFormDto);
      quoteFormValidator.validate(quoteForm);
      Money quotePrice = quotePriceCalculator.compute(quoteForm);
      Quote quote = quoteFactory.create(quotePrice, quoteForm);
      quoteRepository.create(quote);
      return quoteAssembler.from(quote);
    } catch (QuoteAlreadyCreatedException e) {
      throw new CouldNotRequestQuoteError();
    }
  }

  public void purchaseQuote(QuoteId quoteId) {
    try {
      Quote quote = quoteRepository.getById(quoteId);
      quote.purchase();
      quoteRepository.update(quote);
    } catch (QuoteNotFoundException e) {
      throw new QuoteNotFoundError(quoteId);
    }
  }
}
