package ca.ulaval.glo4003.mediator;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BoundedContextTest {
  @Mock private BoundedContextMediator mediator;

  private BoundedContext subject;
  private Event firstEvent;
  private Event secondEvent;
  private List<Event> events;

  @Before
  public void setUp() {
    subject = new DummyBoundedContext(mediator);
    firstEvent = new DummyEvent(EventChannel.QUOTES, new JSONObject().put("NAME", "FIRST EVENT"));
    secondEvent =
        new DummyEvent(EventChannel.POLICIES, new JSONObject().put("NAME", "SECOND EVENT"));
    events = Arrays.asList(firstEvent, secondEvent);
  }

  @Test
  public void publishing_shouldDelegateToMediator() {
    subject.publish(events);

    verify(mediator).publish(firstEvent);
    verify(mediator).publish(secondEvent);
  }

  class DummyBoundedContext extends BoundedContext {
    DummyBoundedContext(BoundedContextMediator mediator) {
      super(mediator);
    }

    @Override
    void process(Event event) {}
  }
}