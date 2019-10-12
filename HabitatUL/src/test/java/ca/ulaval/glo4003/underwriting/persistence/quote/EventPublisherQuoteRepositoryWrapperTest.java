package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.generator.EventGenerator;
import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherQuoteRepositoryWrapperTest {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private Quote quote;
  @Mock private QuoteRepository quoteRepository;
  @Mock private Mediator mediator;

  private EventPublisherQuoteRepositoryWrapper subject;

  @Before
  public void setUp() {
    subject = new EventPublisherQuoteRepositoryWrapper(quoteRepository, mediator);
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
    int randomNumber = Faker.instance().number().randomDigitNotZero();
    List<Event> events = EventGenerator.createList(randomNumber);
    when(quote.getEvents()).thenReturn(events);

    subject.create(quote);

    verify(mediator).publish(events);
  }

  @Test
  public void updatingQuote_shouldDelegateToQuoteRepository() throws QuoteNotFoundException {
    Quote quote = QuoteGenerator.createValidQuote();

    subject.update(quote);

    verify(quoteRepository).update(quote);
  }

  @Test
  public void updatingQuote_shouldPublishDomainEvents() throws QuoteNotFoundException {
    int randomNumber = Faker.instance().number().randomDigitNotZero();
    List<Event> events = EventGenerator.createList(randomNumber);
    when(quote.getEvents()).thenReturn(events);

    subject.update(quote);

    verify(mediator).publish(events);
  }
}
