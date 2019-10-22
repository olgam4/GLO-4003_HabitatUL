package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.helper.EventGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AggregateRootTest {
  private AggregateRoot subject;

  @Before
  public void setUp() {
    subject = new AggregateRoot();
  }

  @Test
  public void registeringEvent_shouldRegisterEvent() {
    Event firstEvent = EventGenerator.createEvent();
    Event secondEvent = EventGenerator.createEvent();

    subject.registerEvent(firstEvent);
    subject.registerEvent(secondEvent);

    assertEquals(Arrays.asList(firstEvent, secondEvent), subject.getEvents());
  }

  @Test
  public void gettingEvents_shouldClearEventHistory() {
    Event firstEvent = EventGenerator.createEvent();
    Event secondEvent = EventGenerator.createEvent();

    subject.registerEvent(firstEvent);
    subject.registerEvent(secondEvent);
    List<Event> firstEventHistory = subject.getEvents();
    List<Event> secondEventHistory = subject.getEvents();

    assertFalse(firstEventHistory.isEmpty());
    assertTrue(secondEventHistory.isEmpty());
  }
}
