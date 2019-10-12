package ca.ulaval.glo4003.mediator;

import java.util.ArrayList;
import java.util.List;

public class AggregateRoot {
  private List<Event> events = new ArrayList<>();

  protected void registerEvent(Event event) {
    events.add(event);
  }

  public List<Event> getEvents() {
    List<Event> clonedEvents = new ArrayList<>(events);
    events.clear();
    return clonedEvents;
  }
}
