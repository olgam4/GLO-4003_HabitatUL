package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.generator.EventGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConcreteBoundedContextMediatorTest {
  @Mock private BoundedContext firstSubscriber;
  @Mock private BoundedContext secondSubscriber;

  private ConcreteBoundedContextMediator subject;
  private Event firstQuoteEvent;
  private Event claimEvent;
  private Event policyEvent;
  private Event secondQuoteEvent;
  private List<Event> events;

  @Before
  public void setUp() {
    subject = new ConcreteBoundedContextMediator();
    firstQuoteEvent = EventGenerator.createEventWithChannel(EventChannel.QUOTES);
    secondQuoteEvent = EventGenerator.createEventWithChannel(EventChannel.QUOTES);
    claimEvent = EventGenerator.createEventWithChannel(EventChannel.CLAIMS);
    policyEvent = EventGenerator.createEventWithChannel(EventChannel.POLICIES);
    events = Arrays.asList(firstQuoteEvent, claimEvent, policyEvent, secondQuoteEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldReceiveUpdatesAboutSubscribedChannel() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber).process(firstQuoteEvent);
    verify(firstSubscriber).process(secondQuoteEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldNotReceiveUpdatesAboutOtherChannels() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber, never()).process(claimEvent);
    verify(firstSubscriber, never()).process(policyEvent);
  }

  @Test
  public void
      eventChannelSubscribers_withManySubscribedChannels_shouldReceiveUpdatesAboutSubscribedChannels() {
    subject.subscribe(firstSubscriber, Arrays.asList(EventChannel.QUOTES, EventChannel.POLICIES));

    subject.publish(events);

    verify(firstSubscriber).process(firstQuoteEvent);
    verify(firstSubscriber).process(policyEvent);
    verify(firstSubscriber).process(secondQuoteEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldBeAbleToSubscribeToSameChannels() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);
    subject.subscribe(secondSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber).process(firstQuoteEvent);
    verify(firstSubscriber).process(secondQuoteEvent);
    verify(secondSubscriber).process(firstQuoteEvent);
    verify(secondSubscriber).process(secondQuoteEvent);
  }
}
