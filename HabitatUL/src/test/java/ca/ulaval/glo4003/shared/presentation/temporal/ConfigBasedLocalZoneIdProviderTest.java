package ca.ulaval.glo4003.shared.presentation.temporal;

import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZoneId;
import java.util.Properties;

import static ca.ulaval.glo4003.shared.presentation.temporal.ConfigBasedLocalZoneIdProvider.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBasedLocalZoneIdProviderTest {
  private static final String LOCAL_ZONE_ID_OVERRIDDEN_VALUE = "America/Toronto";

  @Mock private ConfigFileReader configFileReader;

  private ConfigBasedLocalZoneIdProvider subject;

  @Test
  public void gettingLocalZoneId_withOverriddenConfiguration_shouldProvideOverriddenValue() {
    Properties properties = new Properties();
    properties.setProperty(LOCAL_ZONE_ID_KEY, LOCAL_ZONE_ID_OVERRIDDEN_VALUE);
    when(configFileReader.read(FILE_PATH)).thenReturn(properties);
    subject = new ConfigBasedLocalZoneIdProvider(configFileReader);

    ZoneId localZoneId = subject.getLocalZoneId();

    ZoneId expected = ZoneId.of(LOCAL_ZONE_ID_OVERRIDDEN_VALUE);
    assertEquals(expected, localZoneId);
  }

  @Test
  public void gettingLocalZoneId_withDefaultConfiguration_shouldProvideDefaultValue() {
    when(configFileReader.read(FILE_PATH)).thenReturn(new Properties());
    subject = new ConfigBasedLocalZoneIdProvider(configFileReader);

    ZoneId localZoneId = subject.getLocalZoneId();

    ZoneId expected = ZoneId.of(LOCAL_ZONE_ID_DEFAULT_VALUE);
    assertEquals(expected, localZoneId);
  }
}
