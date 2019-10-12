package ca.ulaval.glo4003.mediator.exception;

import ca.ulaval.glo4003.mediator.Event;

public class ListenerAlreadyDefinedException extends MediatorException {
  public ListenerAlreadyDefinedException(Class<? extends Event> listeningClass) {
    super(
        String.format(
            "You've tried to define this listener twice: <%s>", listeningClass.getCanonicalName()));
  }
}
