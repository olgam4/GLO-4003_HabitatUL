package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.mediator.exception.ListenerAlreadyDefinedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Listeners {
  static final Consumer VOID_LISTENER = o -> {};

  private Map<Class, Consumer> listenersMap = new HashMap<>();

  <T extends Event> void addListener(Class<T> eventClass, Consumer<T> listener) {
    if (listenersMap.containsKey(eventClass)) throw new ListenerAlreadyDefinedException(eventClass);

    listenersMap.put(eventClass, listener);
  }

  Consumer getListener(Class<?> eventClass) {
    return listenersMap.getOrDefault(eventClass, VOID_LISTENER);
  }
}
