package ca.ulaval.glo4003.mediator.event;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EventPayload extends ValueObject {
  private JSONObject value;

  private EventPayload(JSONObject value) {
    this.value = value;
  }

  public JSONObject getValue() {
    return new JSONObject(value.toString());
  }

  public static class EventPayloadBuilder {
    private JSONObject payload = new JSONObject();

    private EventPayloadBuilder() {}

    public static EventPayloadBuilder anEventPayload() {
      return new EventPayloadBuilder();
    }

    public EventPayloadBuilder withEntry(String key, String value) {
      payload.put(key, value);
      return this;
    }

    public EventPayloadBuilder withEntry(String key, Number value) {
      payload.put(key, value);
      return this;
    }

    public EventPayloadBuilder withObject(String key, EventPayload value) {
      payload.put(key, new JSONObject(value.getValue()));
      return this;
    }

    public EventPayloadBuilder withList(String key, List<EventPayload> values) {
      payload.put(key, new JSONArray(values));
      return this;
    }

    public EventPayload build() {
      return new EventPayload(payload);
    }
  }
}
