package ca.ulaval.glo4003.mediator.newmediator;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ChannelTest {
  private final String EVENT = Faker.instance().name().bloodGroup();

  @Mock private Consumer<String> callback;

  private Channel<String> subject;

  @Before
  public void setUp() {
    subject = new Channel<>();
  }

  @Test
  public void sendingEvent_shouldNotifyConnectedCallbacks() {
    subject.connect(callback);

    subject.send(EVENT);

    verify(callback).accept(EVENT);
  }
}
