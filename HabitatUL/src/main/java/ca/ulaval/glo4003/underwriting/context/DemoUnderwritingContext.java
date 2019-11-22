package ca.ulaval.glo4003.underwriting.context;

import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppServiceImpl;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppServiceLoggingDecorator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.EventPublisherQuoteRepositoryDecorator;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoUnderwritingContext {
  public void execute(Mediator mediator) {
    register(QuoteEffectivePeriodProvider.class, new ConfigBasedQuoteEffectivePeriodProvider());
    register(QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    register(
        QuoteRepository.class,
        new EventPublisherQuoteRepositoryDecorator(new InMemoryQuoteRepository(), mediator));
    register(QuoteAppService.class, new QuoteAppServiceLoggingDecorator(new QuoteAppServiceImpl()));
  }
}
