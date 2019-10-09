package ca.ulaval.glo4003.gateway.presentation.databind;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;

import java.time.ZoneId;
import java.util.Properties;

public class ConfigBasedLocalZoneIdProvider implements LocalZoneIdProvider {
  private ZoneId zoneId;

  public ConfigBasedLocalZoneIdProvider() {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    zoneId = ZoneId.of(properties.getProperty("local.zoneId"));
  }

  @Override
  public ZoneId getLocalZoneId() {
    return zoneId;
  }
}
