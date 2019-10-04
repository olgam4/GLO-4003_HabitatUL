package ca.ulaval.glo4003.builder;

import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.mediator.event.EventPayload.EventPayloadBuilder;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

public class EventBuilder {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  private final EventChannel DEFAULT_EVENT_CHANNEL = createEventChannel();
  private final String DEFAULT_EVENT_TYPE = createEventType();
  private final EventPayload DEFAULT_PAYLOAD = createPayload();

  private EventChannel eventChannel = DEFAULT_EVENT_CHANNEL;
  private String eventType = DEFAULT_EVENT_TYPE;
  private EventPayload payload = DEFAULT_PAYLOAD;

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

  private static EventPayload createPayload() {
    Faker faker = Faker.instance();
    return EventPayloadBuilder.anEventPayload()
        .withEntry(faker.book().author(), faker.lordOfTheRings().character())
        .withEntry(faker.cat().breed(), faker.dog().coatLength())
        .build();
  }

  public EventBuilder withEventChannel(EventChannel eventChannel) {
    this.eventChannel = eventChannel;
    return this;
  }

  public EventBuilder withEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public EventBuilder withPayload(EventPayload payload) {
    this.payload = payload;
    return this;
  }

  public Event build() {
    return new Event(eventChannel, eventType, payload, FIXED_CLOCK_PROVIDER);
  }
}
