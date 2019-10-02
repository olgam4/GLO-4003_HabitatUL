package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.generator.EventGenerator;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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
    Map<String, Object> mutatedPayload = subject.getPayload();
    mutatedPayload.put("KEY", "BEFORE_DATE_VALUE");

    assertNotEquals(subject.getPayload(), mutatedPayload);
  }
}
