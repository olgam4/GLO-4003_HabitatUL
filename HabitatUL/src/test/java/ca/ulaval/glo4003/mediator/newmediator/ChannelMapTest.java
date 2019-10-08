package ca.ulaval.glo4003.mediator.newmediator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class ChannelMapTest {
  private ChannelMap subject;

  @Before
  public void setUp() {
    subject = new ChannelMap();
  }

  @Test
  public void gettingChannel_withUnknownChannelClass_shouldCreateNewChannel() {
    assertNotNull(subject.getChannel(String.class));
  }

  @Test
  public void gettingChannel_withKnownChannelClass_shouldReturnExistingChannel() {
    Channel<Integer> channel = subject.getChannel(Integer.class);

    assertSame(channel, subject.getChannel(Integer.class));
  }
}
