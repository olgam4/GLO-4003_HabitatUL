package ca.ulaval.glo4003.mediator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MediatorTest {
  @Mock private Function<FirstEvent, SecondEvent> channel;
  @Mock private Function<FirstEvent, ThirdEvent> anotherChannel;
  @Mock private Consumer<SecondEvent> listener;
  @Mock private Consumer<ThirdEvent> anotherListener;

  private Mediator subject;
  private FirstEvent firstEvent = new FirstEvent();
  private SecondEvent secondEvent = new SecondEvent();
  private ThirdEvent thirdEvent = new ThirdEvent();

  @Before
  public void setUp() {
    when(channel.apply(firstEvent)).thenReturn(secondEvent);
    when(anotherChannel.apply(firstEvent)).thenReturn(thirdEvent);

    subject = new Mediator();
  }

  @Test
  public void publishingEvents_withoutMappedChannel_shouldNotThrow() {
    subject.publish(Arrays.asList(firstEvent));
  }

  @Test
  public void publishingEvents_withMappedChannelAndNoListener_shouldNotThrow() {
    subject.addChannel(FirstEvent.class, SecondEvent.class, channel);

    subject.publish(Arrays.asList(firstEvent));
  }

  @Test
  public void publishingEvent_withMappedChannelAndListener_shouldSendEventOnMappedChannel() {
    subject.addChannel(FirstEvent.class, SecondEvent.class, channel);
    subject.addListener(SecondEvent.class, listener);

    subject.publish(Arrays.asList(firstEvent));

    verify(listener).accept(secondEvent);
  }

  @Test
  public void
      publishingEvent_withMultipleMappedChannelsAndListeners_shouldSendEventOnAllMappedChannels() {
    subject.addChannel(FirstEvent.class, SecondEvent.class, channel);
    subject.addListener(SecondEvent.class, listener);
    subject.addChannel(FirstEvent.class, ThirdEvent.class, anotherChannel);
    subject.addListener(ThirdEvent.class, anotherListener);

    subject.publish(Arrays.asList(firstEvent));

    verify(listener).accept(secondEvent);
    verify(anotherListener).accept(thirdEvent);
  }

  private class FirstEvent extends Event {}

  private class SecondEvent extends Event {}

  private class ThirdEvent extends Event {}
}
