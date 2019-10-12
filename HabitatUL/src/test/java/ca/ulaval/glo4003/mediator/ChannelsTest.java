package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.mediator.exception.ChannelAlreadyDefinedException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ChannelsTest {
  private final Function<ExistingInputEvent, FirstOutputEvent> FIRST_EVENT_MAPPER =
      event -> new FirstOutputEvent();
  private final Function<ExistingInputEvent, SecondOutputEvent> SECOND_EVENT_MAPPER =
      event -> new SecondOutputEvent();

  private Channels subject;

  @Before
  public void setUp() {
    subject = new Channels();
  }

  @Test(expected = ChannelAlreadyDefinedException.class)
  public void addingChannel_withChannelAlreadyDefined_shouldThrow() {
    subject.addChannel(ExistingInputEvent.class, FirstOutputEvent.class, FIRST_EVENT_MAPPER);
    subject.addChannel(ExistingInputEvent.class, FirstOutputEvent.class, event -> null);
  }

  @Test
  public void gettingChannels_withNotExistingInputEventClass_shouldReturnNoChannel() {
    Map<Class, Function> channels = subject.getChannels(NotExistingInputEvent.class);

    assertEquals(0, channels.size());
  }

  @Test
  public void gettingChannels_withExistingInputEventClass_shouldReturnMappedChannel() {
    subject.addChannel(ExistingInputEvent.class, FirstOutputEvent.class, FIRST_EVENT_MAPPER);

    Map<Class, Function> channels = subject.getChannels(ExistingInputEvent.class);

    assertEquals(1, channels.size());
  }

  @Test
  public void
      gettingChannels_withExistingInputEventClassAndMultipleChannels_shouldReturnAllMappedChannels() {
    subject.addChannel(ExistingInputEvent.class, FirstOutputEvent.class, FIRST_EVENT_MAPPER);
    subject.addChannel(ExistingInputEvent.class, SecondOutputEvent.class, SECOND_EVENT_MAPPER);

    Map<Class, Function> channels = subject.getChannels(ExistingInputEvent.class);

    assertEquals(2, channels.size());
  }

  private class NotExistingInputEvent extends Event {}

  private class ExistingInputEvent extends Event {}

  private class FirstOutputEvent extends Event {}

  private class SecondOutputEvent extends Event {}
}
