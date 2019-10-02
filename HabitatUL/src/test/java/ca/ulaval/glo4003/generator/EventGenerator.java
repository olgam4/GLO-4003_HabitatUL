package ca.ulaval.glo4003.generator;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.EventChannel;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventGenerator {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  private EventGenerator() {}

  public static List<Event> createList(int length) {
    return IntStream.range(0, length).mapToObj(i -> createEvent()).collect(Collectors.toList());
  }

  public static Event createEvent() {
    return new DummyEvent(createEventChannel(), createEventType(), createPayload());
  }

  public static Event createEventWithChannel(EventChannel eventChannel) {
    return new DummyEvent(eventChannel, createEventType(), createPayload());
  }

  private static EventChannel createEventChannel() {
    List<EventChannel> eventChannelList = Arrays.asList(EventChannel.values());
    return eventChannelList.get(
        Faker.instance().number().numberBetween(0, eventChannelList.size()));
  }

  private static String createEventType() {
    return Faker.instance().name().title();
  }

  private static HashMap<String, Object> createPayload() {
    Faker faker = Faker.instance();
    return new HashMap<String, Object>() {
      {
        put(faker.book().author(), faker.lordOfTheRings().character());
        put(faker.cat().breed(), faker.dog().coatLength());
      }
    };
  }

  static class DummyEvent extends Event {
    DummyEvent(EventChannel channel, String type, Map<String, Object> payload) {
      super(channel, type, payload, FIXED_CLOCK_PROVIDER);
    }
  }
}
