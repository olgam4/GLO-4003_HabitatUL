package ca.ulaval.glo4003.mediator.newmediator;

public interface EventSender<T> {
  void send(T event);
}
