package ca.ulaval.glo4003.mediator;

import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class MediatorIT {
  private static final Number NUMBER_GENERATOR = Faker.instance().number();
  private static final Double SECOND_EVENT_ATTR1 = NUMBER_GENERATOR.randomDouble(10, 0, 1000);
  private static final Long SECOND_EVENT_ATTR2 = NUMBER_GENERATOR.randomNumber();
  private static final String ATTR1 = SECOND_EVENT_ATTR1.toString();
  private static final String ATTR2 = SECOND_EVENT_ATTR2.toString();
  private static final String THIRD_EVENT_ATTR1 = "third" + ATTR1;
  private static final String THIRD_EVENT_ATTR2 = "third" + ATTR2;

  private Mediator subject;
  private PublisherBC publisherBC;
  private ListenerBC listenerBC;
  private AnotherListenerBC anotherListenerBC;

  @Before
  public void setUp() {
    subject = new Mediator();
    publisherBC = new PublisherBC(subject);

    listenerBC = new ListenerBC();
    Function<FirstEvent, SecondEvent> FirstSecondChannel =
        firstEvent ->
            new SecondEvent(Double.parseDouble(firstEvent.attr1), Long.parseLong(firstEvent.attr2));
    subject.addChannel(FirstEvent.class, SecondEvent.class, FirstSecondChannel);
    Consumer<SecondEvent> secondEventConsumer = event -> listenerBC.process(event);
    subject.addListener(SecondEvent.class, secondEventConsumer);

    anotherListenerBC = new AnotherListenerBC();
    Function<FirstEvent, ThirdEvent> FirstThirdChannel =
        firstEvent -> new ThirdEvent("third" + firstEvent.attr1, "third" + firstEvent.attr2);
    subject.addChannel(FirstEvent.class, ThirdEvent.class, FirstThirdChannel);
    Consumer<ThirdEvent> thirdEventConsumer = event -> anotherListenerBC.process(event);
    subject.addListener(ThirdEvent.class, thirdEventConsumer);
  }

  @Test
  public void publishingEvent_shouldSendChanneledEventsToEveryListener() {
    publisherBC.publish();

    assertEquals(SECOND_EVENT_ATTR1, listenerBC.event.attr1);
    assertEquals(SECOND_EVENT_ATTR2, listenerBC.event.attr2);
    assertEquals(THIRD_EVENT_ATTR1, anotherListenerBC.event.attr1);
    assertEquals(THIRD_EVENT_ATTR2, anotherListenerBC.event.attr2);
  }

  private class FirstEvent extends Event {
    private String attr1;
    private String attr2;

    FirstEvent(String attr1, String attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }

  private class SecondEvent extends Event {
    private Double attr1;
    private Long attr2;

    SecondEvent(Double attr1, Long attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }

  private class ThirdEvent extends Event {
    private String attr1;
    private String attr2;

    ThirdEvent(String attr1, String attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }

  private class PublisherBC {
    private Mediator mediator;

    PublisherBC(Mediator mediator) {
      this.mediator = mediator;
    }

    void publish() {
      Event event = new FirstEvent(ATTR1, ATTR2);
      mediator.publish(event);
    }
  }

  private class ListenerBC {
    private SecondEvent event =
        new SecondEvent(NUMBER_GENERATOR.randomDouble(0, 0, 1000), NUMBER_GENERATOR.randomNumber());

    void process(SecondEvent event) {
      this.event = event;
    }
  }

  private class AnotherListenerBC {
    private ThirdEvent event =
        new ThirdEvent(
            Faker.instance().educator().campus(), Faker.instance().medical().diseaseName());

    void process(ThirdEvent event) {
      this.event = event;
    }
  }
}
