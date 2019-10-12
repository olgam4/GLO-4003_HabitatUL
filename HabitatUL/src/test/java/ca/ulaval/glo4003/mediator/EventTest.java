package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.generator.EventGenerator;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
}
