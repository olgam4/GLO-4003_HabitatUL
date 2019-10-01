package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class EventTest {
  private static final EventChannel AN_EVENT_CHANNEL = EventChannel.QUOTES;
  private static final EventChannel ANOTHER_EVENT_CHANNEL = EventChannel.CLAIMS;
  private static final JSONObject EMPTY_PAYLOAD = new JSONObject();
  private static final FixedClockProvider CLOCK_PROVIDER = new FixedClockProvider();

  private Event subject;

  @Before
  public void setUp() {
    subject = new DummyEvent(AN_EVENT_CHANNEL, EMPTY_PAYLOAD);
  }

  @Test
  public void events_shouldBeValueComparable() {
    assertTrue(subject instanceof ValueComparableObject);
  }

  @Test
  public void events_shouldBeTimestamped() {
    Instant expectedTimestamp = CLOCK_PROVIDER.getClock().instant();
    assertEquals(expectedTimestamp, subject.getTimestamp());
  }

  @Test
  public void events_shouldBeImmutable() {
    Instant mutatedTimestamp = subject.getTimestamp().plus(Duration.of(4, ChronoUnit.DAYS));
    EventChannel mutatedChannel = subject.getChannel();
    mutatedChannel = ANOTHER_EVENT_CHANNEL;
    JSONObject mutatedPayload = subject.getPayload();
    mutatedPayload.put("KEY", "BEFORE_DATE_VALUE");

    assertNotEquals(subject.getTimestamp(), mutatedTimestamp);
    assertNotEquals(subject.getChannel(), mutatedChannel);
    assertNotEquals(subject.getPayload(), mutatedPayload);
  }

  private class DummyEvent extends Event {
    public DummyEvent(EventChannel channel, JSONObject payload) {
      super(channel, payload, CLOCK_PROVIDER);
    }
  }
}
