package ca.ulaval.glo4003.mediator.exception;

import ca.ulaval.glo4003.mediator.Event;

public class ChannelAlreadyDefinedException extends MediatorException {
  public ChannelAlreadyDefinedException(Class<? extends Event> from, Class<? extends Event> to) {
    super(
        String.format(
            "You've tried to define this channel twice: <%s -> %s>",
            from.getCanonicalName(), to.getCanonicalName()));
  }
}
