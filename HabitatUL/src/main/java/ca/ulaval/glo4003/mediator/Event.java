package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import org.json.JSONObject;

import java.time.Instant;

public class Event extends ValueComparableObject {
  private final Instant timestamp;
  private final EventChannel channel;
  private final JSONObject payload;

  public Event(EventChannel channel, JSONObject payload, ClockProvider clockProvider) {
    this.timestamp = Instant.now(clockProvider.getClock());
    this.channel = channel;
    this.payload = payload;
  }

  public EventChannel getChannel() {
    return channel;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public JSONObject getPayload() {
    return new JSONObject(payload);
  }
}
