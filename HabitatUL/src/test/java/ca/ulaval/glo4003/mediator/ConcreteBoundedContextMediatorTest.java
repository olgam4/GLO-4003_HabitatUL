package ca.ulaval.glo4003.mediator;

import org.json.JSONObject;
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
  private Event firstEvent;
  private Event secondEvent;
  private Event thirdEvent;
  private Event fourthEvent;
  private List<Event> events;

  @Before
  public void setUp() {
    subject = new ConcreteBoundedContextMediator();
    firstEvent = new DummyEvent(EventChannel.QUOTES, new JSONObject().put("NAME", "FIRST EVENT"));
    secondEvent = new DummyEvent(EventChannel.CLAIMS, new JSONObject().put("NAME", "SECOND EVENT"));
    thirdEvent = new DummyEvent(EventChannel.POLICIES, new JSONObject().put("NAME", "THIRD EVENT"));
    fourthEvent = new DummyEvent(EventChannel.QUOTES, new JSONObject().put("NAME", "FOURTH EVENT"));
    events = Arrays.asList(firstEvent, secondEvent, thirdEvent, fourthEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldReceiveUpdatesAboutSubscribedChannel() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber).process(firstEvent);
    verify(firstSubscriber).process(fourthEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldNotReceiveUpdatesAboutOtherChannels() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber, never()).process(secondEvent);
    verify(firstSubscriber, never()).process(thirdEvent);
  }

  @Test
  public void
      eventChannelSubscribers_withManySubscribedChannels_shouldReceiveUpdatesAboutSubscribedChannels() {
    subject.subscribe(firstSubscriber, Arrays.asList(EventChannel.QUOTES, EventChannel.POLICIES));

    subject.publish(events);

    verify(firstSubscriber).process(firstEvent);
    verify(firstSubscriber).process(thirdEvent);
    verify(firstSubscriber).process(fourthEvent);
  }

  @Test
  public void eventChannelSubscribers_shouldBeAbleToSubscribeToSameChannels() {
    subject.subscribe(firstSubscriber, EventChannel.QUOTES);
    subject.subscribe(secondSubscriber, EventChannel.QUOTES);

    subject.publish(events);

    verify(firstSubscriber).process(firstEvent);
    verify(firstSubscriber).process(fourthEvent);
    verify(secondSubscriber).process(firstEvent);
    verify(secondSubscriber).process(fourthEvent);
  }
}
