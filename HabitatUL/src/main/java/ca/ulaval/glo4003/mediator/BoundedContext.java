package ca.ulaval.glo4003.mediator;

import java.util.List;

public abstract class BoundedContext {
  private BoundedContextMediator mediator;

  public BoundedContext(BoundedContextMediator mediator) {
    this.mediator = mediator;
  }

  public void publish(Event event) {
    mediator.publish(event);
  }

  public void publish(List<Event> events) {
    events.forEach(this::publish);
  }

  abstract void process(Event event);
}
