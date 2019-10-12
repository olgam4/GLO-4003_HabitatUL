package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;

public class EventPublisherQuoteRepositoryWrapper implements QuoteRepository {
  private QuoteRepository quoteRepository;
  private Mediator mediator;

  public EventPublisherQuoteRepositoryWrapper(QuoteRepository quoteRepository, Mediator mediator) {
    this.quoteRepository = quoteRepository;
    this.mediator = mediator;
  }

  @Override
  public Quote getById(QuoteId quoteId) throws QuoteNotFoundException {
    return quoteRepository.getById(quoteId);
  }

  @Override
  public void create(Quote quote) throws QuoteAlreadyCreatedException {
    quoteRepository.create(quote);
    publishEvents(quote);
  }

  @Override
  public void update(Quote quote) throws QuoteNotFoundException {
    quoteRepository.update(quote);
    publishEvents(quote);
  }

  private void publishEvents(Quote quote) {
    mediator.publish(quote.getEvents());
  }
}
