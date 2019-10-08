package ca.ulaval.glo4003.mediator.newmediator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Channel<T> implements EventSender<T>, EventReceiver<T> {
  private List<Consumer<T>> callbacks;

  public Channel() {
    callbacks = new ArrayList<>();
  }

  @Override
  public void send(T event) {
    for (Consumer<T> callback : callbacks) {
      callback.accept(event);
    }
  }

  @Override
  public void connect(Consumer<T> callback) {
    callbacks.add(callback);
  }
}
