package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.shared.helper.EventGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherQuoteRepositoryDecoratorTest {
  private static final QuoteId QUOTE_ID = QuoteGenerator.createQuoteId();
  private static final List<Event> EVENTS = EventGenerator.createEvents();

  @Mock private Quote quote;
  @Mock private QuoteRepository quoteRepository;
  @Mock private Mediator mediator;

  private EventPublisherQuoteRepositoryDecorator subject;

  @Before
  public void setUp() {
    subject = new EventPublisherQuoteRepositoryDecorator(quoteRepository, mediator);
  }

  @Test
  public void gettingQuoteById_shouldDelegateToQuoteRepository() throws QuoteNotFoundException {
    subject.getById(QUOTE_ID);

    verify(quoteRepository).getById(QUOTE_ID);
  }

  @Test
  public void creatingQuote_shouldDelegateToQuoteRepository() throws QuoteAlreadyCreatedException {
    subject.create(quote);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void creatingQuote_shouldPublishDomainEvents() throws QuoteAlreadyCreatedException {
    when(quote.getEvents()).thenReturn(EVENTS);

    subject.create(quote);

    verify(mediator).publish(EVENTS);
  }

  @Test
  public void updatingQuote_shouldDelegateToQuoteRepository() throws QuoteNotFoundException {
    Quote quote = QuoteGenerator.createQuote();

    subject.update(quote);

    verify(quoteRepository).update(quote);
  }

  @Test
  public void updatingQuote_shouldPublishDomainEvents() throws QuoteNotFoundException {
    when(quote.getEvents()).thenReturn(EVENTS);

    subject.update(quote);

    verify(mediator).publish(EVENTS);
  }
}
