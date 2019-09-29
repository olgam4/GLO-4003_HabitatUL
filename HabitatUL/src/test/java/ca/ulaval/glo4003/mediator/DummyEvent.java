package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.json.JSONObject;

class DummyEvent extends Event {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  DummyEvent(EventChannel channel, JSONObject payload) {
    super(channel, payload, FIXED_CLOCK_PROVIDER);
  }
}
