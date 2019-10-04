package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.builder.EventBuilder;
import ca.ulaval.glo4003.generator.EventGenerator;
import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {
  private Event subject;

  @Before
  public void setUp() {
    subject = EventGenerator.createEvent();
  }

  @Test
  public void events_shouldBeValueComparable() {
    assertTrue(subject instanceof ValueComparableObject);
  }

  @Test
  public void events_shouldBeTimestamped() {
    assertNotNull(subject.getTimestamp());
  }

  @Test
  public void events_shouldBeImmutable() {
    JSONObject payload = subject.getPayload().getValue();
    payload.put("KEY", "BEFORE_DATE_VALUE");

    assertNotEquals(subject.getPayload(), payload);
  }

  @Test
  public void gettingAttribute_shouldReturnCorrespondingValue() {
    EventPayload payload =
        EventPayload.EventPayloadBuilder.anEventPayload().withEntry("key", "value").build();
    subject = EventBuilder.anEvent().withPayload(payload).build();

    Object observedValue = subject.get("key");

    assertEquals("value", observedValue);
  }
}
