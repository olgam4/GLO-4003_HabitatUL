package ca.ulaval.glo4003.gateway.presentation.common.databind;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;

import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedLocalZoneIdProvider implements LocalZoneIdProvider {
  static final String FILE_PATH = "config.properties";
  static final String LOCAL_ZONE_ID_KEY = "local.zoneId";
  static final String LOCAL_ZONE_ID_DEFAULT_VALUE = "UTC";

  private ZoneId zoneId;

  public ConfigBasedLocalZoneIdProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedLocalZoneIdProvider(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String zoneIdValue =
        Optional.ofNullable(properties.getProperty(LOCAL_ZONE_ID_KEY))
            .orElse(LOCAL_ZONE_ID_DEFAULT_VALUE);
    zoneId = ZoneId.of(zoneIdValue);
  }

  @Override
  public ZoneId getLocalZoneId() {
    return zoneId;
  }
}
