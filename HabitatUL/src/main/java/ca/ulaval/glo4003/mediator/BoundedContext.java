package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.mediator.event.Event;

public interface BoundedContext {
  void process(Event event);
}
