package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.application.quote.exception.InvalidEffectiveDateException;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import java.time.Period;

public class QuoteAppService {
  public static final int NUMBER_OF_MONTHS_OF_COVERAGE = 12;
  private QuoteAssembler quoteAssembler;
  private QuotePremiumCalculator quotePremiumCalculator;
  private QuoteRepository quoteRepository;
  private QuoteFactory quoteFactory;
  private ClockProvider clockProvider;

  public QuoteAppService() {
    this(
        new QuoteAssembler(),
        ServiceLocator.resolve(QuotePremiumCalculator.class),
        new QuoteFactory(
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class),
        ServiceLocator.resolve(ClockProvider.class));
  }

  public QuoteAppService(
      QuoteAssembler quoteAssembler,
      QuotePremiumCalculator quotePremiumCalculator,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository,
      ClockProvider clockProvider) {
    this.quoteAssembler = quoteAssembler;
    this.quotePremiumCalculator = quotePremiumCalculator;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
    this.clockProvider = clockProvider;
  }

  public QuoteDto requestQuote(QuoteFormDto quoteFormDto) {
    validateEffectiveDate(quoteFormDto.getEffectiveDate());
    QuoteForm quoteForm = quoteAssembler.from(quoteFormDto);
    Premium premium = quotePremiumCalculator.computeQuotePremium(quoteForm);
    Quote quote = quoteFactory.create(premium, quoteForm);
    quoteRepository.create(quote);
    return quoteAssembler.from(quote);
  }

  public void purchaseQuote(QuoteId quoteId) {
    Quote quote = quoteRepository.getById(quoteId);
    quote.purchase();
    quoteRepository.update(quote);
  }

  private void validateEffectiveDate(Date effectiveDate) {
    if (checkValidEffectiveDate(effectiveDate)) {
      throw new InvalidEffectiveDateException();
    }
  }

  private boolean checkValidEffectiveDate(Date effectiveDate) {
    Date now = Date.now(clockProvider.getClock());
    boolean tooLate = now.isAfter(effectiveDate);
    boolean tooSoon =
        now.plus(Period.ofMonths(NUMBER_OF_MONTHS_OF_COVERAGE)).isBefore(effectiveDate);
    return tooLate || tooSoon;
  }
}
