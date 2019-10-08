package ca.ulaval.glo4003.mediator.newmediator;

import java.util.function.Consumer;

public interface EventReceiver<T> {
  void connect(Consumer<T> callback);
}
