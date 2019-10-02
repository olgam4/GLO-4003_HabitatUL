package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.mediator.BoundedContextMediator;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;

public class EventPublisherQuoteRepositoryWrapper implements QuoteRepository {
  private QuoteRepository quoteRepository;
  private BoundedContextMediator mediator;

  public EventPublisherQuoteRepositoryWrapper(
      QuoteRepository quoteRepository, BoundedContextMediator mediator) {
    this.quoteRepository = quoteRepository;
    this.mediator = mediator;
  }

  @Override
  public Quote getById(QuoteId quoteId) {
    return quoteRepository.getById(quoteId);
  }

  @Override
  public void create(Quote quote) {
    quoteRepository.create(quote);
    publishEvents(quote);
  }

  @Override
  public void update(Quote quote) {
    quoteRepository.update(quote);
    publishEvents(quote);
  }

  private void publishEvents(Quote quote) {
    mediator.publish(quote.getEvents());
  }
}
