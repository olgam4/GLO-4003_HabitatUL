package ca.ulaval.glo4003.mediator.event;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;

import java.time.Instant;

public class Event extends ValueComparableObject {
  private final Instant timestamp;
  private final EventChannel channel;
  private final String type;
  private final EventPayload payload;

  public Event(
      EventChannel channel, String type, EventPayload payload, ClockProvider clockProvider) {
    this.timestamp = Instant.now(clockProvider.getClock());
    this.channel = channel;
    this.type = type;
    this.payload = payload;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public EventChannel getChannel() {
    return channel;
  }

  public String getType() {
    return type;
  }

  public EventPayload getPayload() {
    return payload;
  }

  public Object get(String key) {
    return payload.getValue().get(key);
  }
}
