package ca.ulaval.glo4003.mediator;

import java.util.List;

public interface BoundedContextMediator {
  void subscribe(BoundedContext boundedContext, EventChannel channel);

  void subscribe(BoundedContext boundedContext, List<EventChannel> channels);

  void publish(Event event);

  void publish(List<Event> events);
}
