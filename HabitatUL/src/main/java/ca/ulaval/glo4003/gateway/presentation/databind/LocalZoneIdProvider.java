package ca.ulaval.glo4003.gateway.presentation.databind;

import java.time.ZoneId;

public interface LocalZoneIdProvider {
  ZoneId getLocalZoneId();
}
