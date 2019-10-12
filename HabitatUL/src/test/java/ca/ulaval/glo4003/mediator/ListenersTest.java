package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.mediator.exception.ListenerAlreadyDefinedException;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static ca.ulaval.glo4003.mediator.Listeners.VOID_LISTENER;
import static org.junit.Assert.assertEquals;

public class ListenersTest {
  private static final Consumer<ExistingOutputEvent> LISTENER = ExistingOutputEvent::getClass;

  private Listeners subject;

  @Before
  public void setUp() {
    subject = new Listeners();
  }

  @Test(expected = ListenerAlreadyDefinedException.class)
  public void addingListener_withListenerAlreadyDefined_shouldThrow() {
    subject.addListener(ExistingOutputEvent.class, LISTENER);
    subject.addListener(ExistingOutputEvent.class, VOID_LISTENER);
  }

  @Test
  public void gettingListener_withNotExistingOutputEventClass_shouldReturnVoidListener() {
    Consumer listener = subject.getListener(NotExistingOutputEvent.class);

    assertEquals(VOID_LISTENER, listener);
  }

  @Test
  public void gettingListener_withExistingOutputEventClass_shouldReturnMappedListener() {
    subject.addListener(ExistingOutputEvent.class, LISTENER);

    Consumer listener = subject.getListener(ExistingOutputEvent.class);

    assertEquals(LISTENER, listener);
  }

  private class ExistingOutputEvent extends Event {}

  private class NotExistingOutputEvent extends Event {}
}
