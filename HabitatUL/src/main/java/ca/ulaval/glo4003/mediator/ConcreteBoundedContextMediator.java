package ca.ulaval.glo4003.mediator;

import java.util.*;

public class ConcreteBoundedContextMediator implements BoundedContextMediator {
  private Map<EventChannel, Set<BoundedContext>> subscribers = new EnumMap<>(EventChannel.class);

  @Override
  public synchronized void subscribe(BoundedContext boundedContext, EventChannel channel) {
    Set<BoundedContext> boundedContexts = subscribers.getOrDefault(channel, new HashSet<>());
    boundedContexts.add(boundedContext);
    subscribers.put(channel, boundedContexts);
  }

  @Override
  public synchronized void subscribe(BoundedContext boundedContext, List<EventChannel> channels) {
    channels.forEach(channel -> subscribe(boundedContext, channel));
  }

  @Override
  public void publish(Event event) {
    Set<BoundedContext> boundedContexts =
        subscribers.getOrDefault(event.getChannel(), new HashSet<>());
    boundedContexts.forEach(boundedContext -> boundedContext.process(event));
  }

  @Override
  public void publish(List<Event> events) {
    events.forEach(this::publish);
  }
}
