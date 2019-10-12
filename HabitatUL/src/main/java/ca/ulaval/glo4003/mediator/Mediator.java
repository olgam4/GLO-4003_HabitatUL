package ca.ulaval.glo4003.mediator;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Mediator {
  private Channels channels = new Channels();
  private Listeners listeners = new Listeners();

  public <T extends Event, U extends Event> void addChannel(
      Class<T> inputClass, Class<U> outputClass, Function<T, U> channel) {
    channels.addChannel(inputClass, outputClass, channel);
  }

  public <T extends Event> void addListener(Class<T> eventClass, Consumer<T> callback) {
    listeners.addListener(eventClass, callback);
  }

  public void publish(List<Event> events) {
    events.forEach(this::publish);
  }

  public void publish(Event event) {
    channels
        .getChannels(event.getClass())
        .forEach(
            (outputEventClass, channel) ->
                listeners.getListener(outputEventClass).accept(channel.apply(event)));
  }
}
