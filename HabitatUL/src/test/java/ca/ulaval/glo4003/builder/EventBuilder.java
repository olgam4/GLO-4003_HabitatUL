package ca.ulaval.glo4003.builder;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.EventChannel;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBuilder {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  private final EventChannel DEFAULT_EVENT_CHANNEL = createEventChannel();
  private final String DEFAULT_EVENT_TYPE = createEventType();
  private final Map<String, Object> DEFAULT_PAYLOAD = createPayload();

  private EventChannel eventChannel = DEFAULT_EVENT_CHANNEL;
  private String eventType = DEFAULT_EVENT_TYPE;
  private Map<String, Object> payload = DEFAULT_PAYLOAD;

  private EventBuilder() {}

  public static EventBuilder anEvent() {
    return new EventBuilder();
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

  public EventBuilder withEventChannel(EventChannel eventChannel) {
    this.eventChannel = eventChannel;
    return this;
  }

  public EventBuilder withEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public Event build() {
    return new Event(eventChannel, eventType, payload, FIXED_CLOCK_PROVIDER);
  }
}
