package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.CoverageAppService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.*;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;

public class QuoteAppServiceImpl implements QuoteAppService {
  private QuoteAssembler quoteAssembler;
  private CoverageAppService coverageAppService;
  private QuoteFactory quoteFactory;
  private QuoteRepository quoteRepository;

  public QuoteAppServiceImpl() {
    this(
        new QuoteAssembler(),
        new CoverageAppService(),
        new QuoteFactory(
            ServiceLocator.resolve(QuoteValidityPeriodProvider.class),
            ServiceLocator.resolve(QuoteEffectivePeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(QuoteRepository.class));
  }

  public QuoteAppServiceImpl(
      QuoteAssembler quoteAssembler,
      CoverageAppService coverageAppService,
      QuoteFactory quoteFactory,
      QuoteRepository quoteRepository) {
    this.quoteAssembler = quoteAssembler;
    this.coverageAppService = coverageAppService;
    this.quoteFactory = quoteFactory;
    this.quoteRepository = quoteRepository;
  }

  public QuoteDto requestQuote(RequestQuoteDto requestQuoteDto) {
    try {
      QuoteForm quoteForm = quoteAssembler.from(requestQuoteDto);
      CoverageDto coverageDto = coverageAppService.requestQuoteCoverage(quoteForm);
      Quote quote =
          quoteFactory.create(
              quoteForm, coverageDto.getCoverageDetails(), coverageDto.getPremiumDetails());
      quoteRepository.create(quote);
      return quoteAssembler.from(quote);
    } catch (QuoteAlreadyCreatedException e) {
      throw new CouldNotRequestQuoteError(e);
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
