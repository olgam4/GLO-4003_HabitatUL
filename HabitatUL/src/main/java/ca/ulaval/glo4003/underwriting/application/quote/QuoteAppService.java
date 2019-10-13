package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.InvalidEffectiveDateError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import java.time.Period;

public class QuoteAppService {
  private static final int NUMBER_OF_MONTHS_OF_COVERAGE = 12;

  private QuoteAssembler quoteAssembler;
  private QuotePriceCalculator quotePriceCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;
  private ClockProvider clockProvider;

  public QuoteAppService() {
    this(
        new QuoteAssembler(),
        new QuotePriceCalculator(),
        new QuoteFactory(
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class),
        ServiceLocator.resolve(ClockProvider.class));
  }

  public QuoteAppService(
      QuoteAssembler quoteAssembler,
      QuotePriceCalculator quotePriceCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository,
      ClockProvider clockProvider) {
    this.quoteAssembler = quoteAssembler;
    this.quotePriceCalculator = quotePriceCalculator;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
    this.clockProvider = clockProvider;
  }

  public QuoteDto requestQuote(QuoteFormDto quoteFormDto) {
    try {
      validateEffectiveDate(quoteFormDto.getEffectiveDate());
      QuoteForm quoteForm = quoteAssembler.from(quoteFormDto);
      Money price = quotePriceCalculator.compute(quoteForm);
      Quote quote = quoteFactory.create(price, quoteForm);
      quoteRepository.create(quote);
      return quoteAssembler.from(quote);
    } catch (QuoteAlreadyCreatedException e) {
      throw new CouldNotRequestQuoteError();
    }
  }

  private void validateEffectiveDate(Date effectiveDate) {
    if (isInvalidEffectiveDate(effectiveDate)) {
      throw new InvalidEffectiveDateError();
    }
  }

  private boolean isInvalidEffectiveDate(Date effectiveDate) {
    Date now = Date.now(clockProvider.getClock());
    boolean tooLate = now.isAfter(effectiveDate);
    boolean tooSoon =
        now.plus(Period.ofMonths(NUMBER_OF_MONTHS_OF_COVERAGE)).isBefore(effectiveDate);
    return tooLate || tooSoon;
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
