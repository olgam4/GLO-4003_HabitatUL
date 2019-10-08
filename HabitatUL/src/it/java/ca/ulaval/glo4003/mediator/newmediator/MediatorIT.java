package ca.ulaval.glo4003.mediator.newmediator;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MediatorIT {
  private final Integer INTEGER_EVENT = Faker.instance().number().randomDigit();
  private final Float FLOAT_EVENT = new Float(INTEGER_EVENT);
  private final String STRING_EVENT = INTEGER_EVENT.toString();

  private Mediator subject;

  @Before
  public void setUp() {
    subject = new Mediator();
  }

  @Test
  public void sendingEvent_withoutMappedChannel_shouldNotThrow() {
    EventSender<String> stringSender = subject.createChannel(String.class);

    stringSender.send(STRING_EVENT);
  }

  @Test
  public void sendingEvent_withSingleMappedChannel_shouldSendEventOnMappedChannel() {
    EventSender<String> stringSender = subject.createChannel(String.class);
    Consumer<Integer> callback = (Consumer<Integer>) mock(Consumer.class);
    subject.subscribe(Integer.class, callback);
    subject.addEventMapper(String.class, Integer.class, Integer::parseInt);

    stringSender.send(STRING_EVENT);

    verify(callback).accept(INTEGER_EVENT);
  }

  @Test
  public void sendingEvent_withMultipleMappedChannels_shouldSendEventOnAllMappedChannels() {
    EventSender<String> stringSender = subject.createChannel(String.class);
    Consumer<Integer> integerCallback = (Consumer<Integer>) mock(Consumer.class);
    subject.subscribe(Integer.class, integerCallback);
    Consumer<Float> floatCallback = (Consumer<Float>) mock(Consumer.class);
    subject.subscribe(Float.class, floatCallback);
    subject.addEventMapper(String.class, Integer.class, Integer::parseInt);
    subject.addEventMapper(String.class, Float.class, Float::parseFloat);

    stringSender.send(STRING_EVENT);

    verify(integerCallback).accept(INTEGER_EVENT);
    verify(floatCallback).accept(FLOAT_EVENT);
  }
}
