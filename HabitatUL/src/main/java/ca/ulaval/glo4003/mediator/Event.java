package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Event extends ValueComparableObject {
  private final Instant timestamp;
  private final EventChannel channel;
  private final String type;
  private final Map<String, Object> payload;

  public Event(
      EventChannel channel, String type, Map<String, Object> payload, ClockProvider clockProvider) {
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

  public Map<String, Object> getPayload() {
    return new HashMap<>(payload);
  }
}
